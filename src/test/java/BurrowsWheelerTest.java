import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;


public class BurrowsWheelerTest {

   private static String DECODED_INPUT = "ABRACADABRA!";
   
   private static byte[] ENCODED_INPUT = { 0x0, 0x0, 0x0, 0x3, 
      0x41, 0x52, 0x44, 0x21, 0x52, 0x43, 0x41, 0x41, 0x41, 
      0x41, 0x42, 0x42 };   
   
   @Test
   public void testTransform() {
      // backup standard in and out
      InputStream standardIn = System.in;
      PrintStream standardOut = System.out;
      try {
         // setup new input
         System.setIn(new ByteArrayInputStream(DECODED_INPUT.getBytes()));
         // create new output stream as byte array and assign to standard
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         System.setOut(new PrintStream(baos));
         
         BurrowsWheeler.transform();
         byte[] encoded = baos.toByteArray();
         assertEquals(ENCODED_INPUT.length, encoded.length);
         for(int i = 0; i < encoded.length; i++) {
           assertEquals(ENCODED_INPUT[i], encoded[i]);
         }
      } 
      finally {
         // return standard input and output
         System.setIn(standardIn);
         System.setOut(standardOut);
      }
   }

   @Test
   public void testInverseTransform() {
      // backup standard in and out
      InputStream standardIn = System.in;
      PrintStream standardOut = System.out;
      try {
         // setup new input with encoded message
         System.setIn(new ByteArrayInputStream(ENCODED_INPUT));
         // create new output stream as byte array and assign to standard
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         System.setOut(new PrintStream(baos));
         
         BurrowsWheeler.inverseTransform();
         String decoded = baos.toString();
         // check length and chars
         assertEquals(DECODED_INPUT.length(), decoded.length());
         assertEquals(DECODED_INPUT, decoded);
      } 
      finally {
         // return standard input and output
         System.setIn(standardIn);
         System.setOut(standardOut);
      }
   }

}