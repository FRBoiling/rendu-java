package util; 

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
/** 
* RandomUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>$Today</pre> 
* @version 1.0 
*/ 
public class RandomUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getRandom(int n) 
* 
*/ 
@Test
public void testGetRandom() throws Exception {
    HashMap <Integer,Integer> keyValue = new HashMap<Integer, Integer>();
    long startTime = System.currentTimeMillis();
    for ( int i =0;i<10000;i++){
        int nV =RandomUtil.getRandom(10000);
        if ( nV >=0 && nV<1000){
            int ncount = 1;
            if ( keyValue.containsKey(1000)){
                ncount = keyValue.get(1000)+1;
                keyValue.put(1000,ncount);
            }
            else {
                keyValue.put(1000,ncount);
            }
        }
        if ( nV >=1000 && nV<2000){
            int ncount = 1;
            if ( keyValue.containsKey(2000)){
                ncount = keyValue.get(2000)+1;
                keyValue.put(2000,ncount);
            }
            else {
                keyValue.put(2000,ncount);
            }
        }
        if ( nV >=2000 && nV<3000){
            int ncount = 1;
            if ( keyValue.containsKey(3000)){
                ncount = keyValue.get(3000)+1;
                keyValue.put(3000,ncount);
            }
            else {
                keyValue.put(3000,ncount);
            }
        }
        if ( nV >=3000 && nV<4000){
            int ncount = 1;
            if ( keyValue.containsKey(4000)){
                ncount = keyValue.get(4000)+1;
                keyValue.put(4000,ncount);
            }
            else {
                keyValue.put(4000,ncount);
            }
        }
        if ( nV >=4000 && nV<5000){
            int ncount = 1;
            if ( keyValue.containsKey(5000)){
                ncount = keyValue.get(5000)+1;
                keyValue.put(5000,ncount);
            }
            else {
                keyValue.put(5000,ncount);
            }
        }
        if ( nV >=5000 && nV<6000){
            int ncount = 1;
            if ( keyValue.containsKey(6000)){
                ncount = keyValue.get(6000)+1;
                keyValue.put(6000,ncount);
            }
            else {
                keyValue.put(6000,ncount);
            }
        }
        if ( nV >=6000 && nV<7000){
            int ncount = 1;
            if ( keyValue.containsKey(7000)){
                ncount = keyValue.get(7000)+1;
                keyValue.put(7000,ncount);
            }
            else {
                keyValue.put(7000,ncount);
            }
        }
        if ( nV >=7000 && nV<8000){
            int ncount = 1;
            if ( keyValue.containsKey(8000)){
                ncount = keyValue.get(8000)+1;
                keyValue.put(8000,ncount);
            }
            else {
                keyValue.put(8000,ncount);
            }
        }
        if ( nV >=8000 && nV<9000){
            int ncount = 1;
            if ( keyValue.containsKey(9000)){
                ncount = keyValue.get(9000)+1;
                keyValue.put(9000,ncount);
            }
            else {
                keyValue.put(9000,ncount);
            }
        }
        if ( nV >=9000 && nV<10000){
            int ncount = 1;
            if ( keyValue.containsKey(10000)){
                ncount = keyValue.get(10000)+1;
                keyValue.put(10000,ncount);
            }
            else {
                keyValue.put(10000,ncount);
            }
        }
    }
    long endTime = System.currentTimeMillis();
    log.debug("总耗时：{} ms",endTime-startTime);
   Iterator iter = keyValue.entrySet().iterator();
    while (iter.hasNext()){
        Map.Entry entry =(Map.Entry) iter.next();
        log.debug("key {} count {}",entry.getKey(),entry.getValue());
    }
}

/** 
* 
* Method: getSecureRandom(int n) 
* 
*/ 
@Test
public void testGetSecureRandom() throws Exception {
    HashMap <Integer,Integer> keyValue = new HashMap<Integer, Integer>();
    long startTime = System.currentTimeMillis();

    for ( int i =0;i<10000;i++){
        int nV =RandomUtil.getSecureRandom(10000);
        if ( nV >=0 && nV<1000){
            int ncount = 1;
            if ( keyValue.containsKey(1000)){
                ncount = keyValue.get(1000)+1;
                keyValue.put(1000,ncount);
            }
            else {
                keyValue.put(1000,ncount);
            }
        }
        if ( nV >=1000 && nV<2000){
            int ncount = 1;
            if ( keyValue.containsKey(2000)){
                ncount = keyValue.get(2000)+1;
                keyValue.put(2000,ncount);
            }
            else {
                keyValue.put(2000,ncount);
            }
        }
        if ( nV >=2000 && nV<3000){
            int ncount = 1;
            if ( keyValue.containsKey(3000)){
                ncount = keyValue.get(3000)+1;
                keyValue.put(3000,ncount);
            }
            else {
                keyValue.put(3000,ncount);
            }
        }
        if ( nV >=3000 && nV<4000){
            int ncount = 1;
            if ( keyValue.containsKey(4000)){
                ncount = keyValue.get(4000)+1;
                keyValue.put(4000,ncount);
            }
            else {
                keyValue.put(4000,ncount);
            }
        }
        if ( nV >=4000 && nV<5000){
            int ncount = 1;
            if ( keyValue.containsKey(5000)){
                ncount = keyValue.get(5000)+1;
                keyValue.put(5000,ncount);
            }
            else {
                keyValue.put(5000,ncount);
            }
        }
        if ( nV >=5000 && nV<6000){
            int ncount = 1;
            if ( keyValue.containsKey(6000)){
                ncount = keyValue.get(6000)+1;
                keyValue.put(6000,ncount);
            }
            else {
                keyValue.put(6000,ncount);
            }
        }
        if ( nV >=6000 && nV<7000){
            int ncount = 1;
            if ( keyValue.containsKey(7000)){
                ncount = keyValue.get(7000)+1;
                keyValue.put(7000,ncount);
            }
            else {
                keyValue.put(7000,ncount);
            }
        }
        if ( nV >=7000 && nV<8000){
            int ncount = 1;
            if ( keyValue.containsKey(8000)){
                ncount = keyValue.get(8000)+1;
                keyValue.put(8000,ncount);
            }
            else {
                keyValue.put(8000,ncount);
            }
        }
        if ( nV >=8000 && nV<9000){
            int ncount = 1;
            if ( keyValue.containsKey(9000)){
                ncount = keyValue.get(9000)+1;
                keyValue.put(9000,ncount);
            }
            else {
                keyValue.put(9000,ncount);
            }
        }
        if ( nV >=9000 && nV<10000){
            int ncount = 1;
            if ( keyValue.containsKey(10000)){
                ncount = keyValue.get(10000)+1;
                keyValue.put(10000,ncount);
            }
            else {
                keyValue.put(10000,ncount);
            }
        }
    }

    long endTime = System.currentTimeMillis();

    log.debug("总耗时：{} ms",endTime-startTime);

    Iterator iter = keyValue.entrySet().iterator();
    while (iter.hasNext()){
        Map.Entry entry =(Map.Entry) iter.next();
        log.debug("key {} count {}",entry.getKey(),entry.getValue());
    }
} 


} 
