package cube.runtime.util.tests;

import cube.runtime.AdministrationService;

/**
 * User: debbabi
 * Date: 9/26/13
 * Time: 3:38 PM
 */
public class Test implements Runnable {


    Thread t;

    AdministrationService as;
    int testId;
    int param;

    public Test(AdministrationService as, int testId) {
        this.as = as;
        this.testId = testId;
        t = new Thread(this);
        t.start();
    }

    public Test(AdministrationService as, int testId, int param) {
        this.as = as;
        this.testId = testId;
        this.param = param;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        switch (testId) {
            case 1: {
                Test1.test(as);
            } break;
            case 2: {
                Test2.test(as);
            }case 3: {
                Test3.test(as, param);
            }
        }
    }



}
