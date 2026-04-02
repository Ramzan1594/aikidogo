package be.technifutur.Model;

import org.junit.jupiter.api.*;

//la convention veut que la classe on met NomTest
//et les methodes on ecrit testMethode()
public class ShowTest {

//    @Test
//    public void test(){
//        System.out.println("test");
//    }

//    @Test
//    public void testTarif(){
//        double x = 10.00;
//        String s ;
//
//        Tarif t = new Tarif(10.00,10.00,10.00,10.00);
//
//        t.getpPlage();
//
//        Assertions.assertEquals(10.00,t.getpPlage());
//    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach
    void after(){
        System.out.println("afterEach");
    }


    @Test
    public void test1(){
        System.out.println("test1");
    }

    @Test
    public void test2(){
        System.out.println("test2");
    }


}
