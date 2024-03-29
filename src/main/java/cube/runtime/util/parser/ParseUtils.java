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

package cube.runtime.util.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Parser Utility Methods.
 * This class contains helper method to parse Archtype.
 * 
 * @author 
 */
public final class ParseUtils {

    /**
     * Parses the string form of an array as {a, b, c}
     * or [a, b, c].
     * @param str the string form
     * @return the resulting string array
     */
    public static String[] parseArrays(String str) {
        if (str.length() == 0) {
            return new String[0];
        }
        
        // Remove { and } or [ and ]
        if ((str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') 
                || (str.charAt(0) == '[' && str.charAt(str.length() - 1) == ']')) {
            String internal = (str.substring(1, str.length() - 1)).trim();
            // Check empty array
            if (internal.length() == 0) {
                return new String[0];
            }
            return split(internal, ",");
        } else {
            return new String[] { str };
        }
    }
    
    /**
     * Parses the string form of an array as {a, b, c}
     * or [a, b, c] as a list.
     * @param str the string form
     * @return the resulting list
     */
    public static List parseArraysAsList(String str) {
        return Arrays.asList(parseArrays(str));
    }
    
    /**
     * Split method. 
     * This method is equivalent of the String.split in java 1.4
     * The result array contains 'trimmed' String
     * @param toSplit the String to split
     * @param separator the separator
     * @return the split array 
     */
    public static String[] split(String toSplit, String separator) {
        StringTokenizer tokenizer = new StringTokenizer(toSplit, separator);
        String[] result = new String[tokenizer.countTokens()];
        int index = 0;
        while (tokenizer.hasMoreElements()) {
            result[index] = tokenizer.nextToken().trim();
            index++;
        }
        return result;
    }
    
    public static InputStream stringToInputStream(String txt) {
    	if (txt != null) {
    		return new ByteArrayInputStream(txt.getBytes());    	
    	}
    	return null;
    }
}
