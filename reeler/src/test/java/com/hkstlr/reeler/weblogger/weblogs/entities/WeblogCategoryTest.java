/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
 * 
 */
package com.hkstlr.reeler.weblogger.weblogs.entities;

import com.hkstlr.reeler.weblogger.TestSetup;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author henry.kastler
 */
public class WeblogCategoryTest {

    WeblogCategory cut;

    public WeblogCategoryTest() {
        //constructor
    }

    @Before
    public void setUp() throws Exception {
        cut = new WeblogCategory();
    }

    @Test
    public void testWeblogCategoryEntity() {
        TestEntityReflector ter = new TestEntityReflector();
        ter.testEntityClass(cut);
    }

    @Test
    public void testCalculatePosition() throws Exception {

        Weblog weblog = TestSetup.getWeblog();

        WeblogCategory newCat = new WeblogCategory(weblog, "Name");

        List<WeblogCategory> cats = new ArrayList<>();
        cats.add(newCat);
        weblog.setWeblogCategories(cats);
        assertEquals(1, newCat.getPosition());

        WeblogCategory newCat2 = new WeblogCategory(weblog, "Name");

        cats.add(newCat);
        cats.add(newCat2);
        weblog.setWeblogCategories(cats);

        assertEquals(2, newCat2.getPosition());

        WeblogCategory newCat3 = new WeblogCategory(weblog, "position test", 10);

        assertEquals(10, newCat3.getPosition());

        weblog = TestSetup.getWeblog();

        String catstr = "General,Technology,Finance";
        int position = 1;

        String[] splitcats = catstr.split(",");

        for (String catName : splitcats) {
            if (catName.trim().length() == 0) {
                continue;
            }
            //System.out.println( blogCats.size());
            WeblogCategory cat = new WeblogCategory(weblog, catName);
            //System.out.println("position: " + position);
            //System.out.println("categoryPosition: " + cat.getPosition());

            weblog.getWeblogCategories().add(cat);
            assertEquals(cat.getPosition(), position);
            position++;
        }

    }

}
