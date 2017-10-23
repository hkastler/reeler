import com.hkstlr.reeler.app.control.AppConstants;
import com.hkstlr.reeler.weblogger.weblogs.boundary.manager.WeblogEntryManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
@Ignore
@RunWith(Arquillian.class)
public class WeblogEntryManagerIT {
 
   @Deployment
   public static JavaArchive createDeployment() {
       return ShrinkWrap.create(JavaArchive.class)
           .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
   }
 
   @Test
   public void arquillianTest() {
       System.out.println("arquillian test");
       Assert.assertTrue("deployed on arquillian",true);
   }
}