/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kpmg.cdd.util;

import java.util.Collection;

/**
 * @author Joram Barrez
 */
public class CollectionUtils {
	
  public static boolean isEmpty(@SuppressWarnings("rawtypes") Collection collection) {
      return (collection == null || collection.isEmpty());
  }

  public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Collection collection) {
      return !CollectionUtils.isEmpty(collection);
  }


}
