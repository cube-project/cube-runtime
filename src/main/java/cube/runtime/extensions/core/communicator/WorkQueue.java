/*
 * Copyright 2011 Adele Team LIG (http://www-adele.imag.fr/)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cube.runtime.extensions.core.communicator;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkQueue {
		private ArrayList threads;
		private LinkedList queue;
		private int maxJobQueued;
		/* Theses 3 references will be injected by iPOJO */
		private String m_name;
		private int m_size;
		private int m_priority;
		
		//private static final Logger logger = LoggerFactory.getLogger("cilia.ipojo.runtime");

		public void WorkQueue() {
		}

		public WorkQueue(String name, int nThread) {
			this.m_name = name;
			this.m_size = nThread;
            this.queue = new LinkedList();
            this.threads = new ArrayList(m_size);
            this.m_priority = Thread.NORM_PRIORITY;
            this.maxJobQueued = 0;
			start();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#start()
		 */
		public void start() {
			for (int i = 0; i < m_size; i++) {
				addThread(i);
			}
			//logger.debug("Work queue size =" + this.m_size);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#stop()
		 */
		public synchronized void stop() {
            int size = 0;
            synchronized (threads) {
			    size = threads.size();
            }
			for (int i = 0; i < size; i++) {
				removeThread();
			}
		}

		private void addThread(int i) {
			Worker worker = new Worker();
            synchronized (threads) {
			    threads.add(worker);
            }
			worker.setDaemon(true) ;
			worker.setName(m_name + "-" + i);
			worker.setPriority(m_priority);
			worker.start();

		}

		private void removeThread() {
			Worker worker;
            synchronized (this.threads) {
                if (threads.size() > 0) {
                    /* extract the worker thread in the list of pool thread */
                    worker = (Worker) threads.get(0);
                    threads.remove(0);
                    worker.stopped = true;
                    synchronized (queue) {
                        queue.notify();
                    }
                }
            }
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#setPriority(int)
		 */
		public void setPriority(int newPriority) {

			if ((m_priority < Thread.NORM_PRIORITY) || (m_priority > Thread.MAX_PRIORITY)) {
				String msg = "priority out of bounds =" + m_priority;
				//logger.error(msg);
				throw new IllegalArgumentException(msg);
			}
            synchronized (this.threads) {
                for (int i = 0; i < threads.size(); i++) {
                    ((Worker) threads.get(i)).setPriority(newPriority);
                }
            }
			/*
			if (logger.isDebugEnabled()) {
				logger.debug(this.m_name + " priority =" + newPriority);
			}*/
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#getPriority()
		 */
		public synchronized int getPriority() {
			return this.m_priority;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#size()
		 */
		public int size() {
			synchronized (threads) {
                return threads.size();
            }
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#size(int)
		 */
		public int size(int newSize) {
			synchronized (threads) {
				if (newSize > threads.size()) {
					for (int i = threads.size(); i < newSize; i++) {
						addThread(i);
					}
				} else {
					int size = threads.size() - newSize;
					for (int i = 0; i < size; i++) {
						removeThread();
					}
				}
			    return threads.size();
            }
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#sizeJobQueued()
		 */
		public int sizeJobQueued() {
            synchronized (queue) {
			    return queue.size();
            }
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * fr.liglab.adele.cilia.framework.utils.WorkQueue#execute(java.lang.Runnable
		 * )
		 */
		public void execute(Runnable job) {
			synchronized (queue) {
				queue.addLast(job);
				maxJobQueued = Math.max(maxJobQueued, queue.size());
				queue.notify();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#getMaxjobQueued()
		 */
		public synchronized int sizeMaxjobQueued() {
			return maxJobQueued;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see fr.liglab.adele.cilia.framework.utils.WorkQueue#resetMaxJobQueued()
		 */
		public synchronized void resetMaxJobQueued() {
			maxJobQueued = 0;
		}

		private class Worker extends Thread {
			public boolean stopped = false;

			public void run() {
				Runnable r;
				while (true) {
					synchronized (queue) {
						while (queue.isEmpty()) {
							try {
								queue.wait();
								if (stopped)
									return;
							} catch (InterruptedException ignored) {
							}
						}
						r = (Runnable) queue.removeFirst();
					}
					try {
						r.run();
					} catch (RuntimeException e) {
						//logger.error("Exception Worker(" + this.getName() + ")=" + e);
					}
				}
			}
		}

	}