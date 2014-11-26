package com.hj.cobar;

import java.util.List;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hj.cobar.bean.Cont;
import com.hj.cobar.query.ContQuery;
import com.hj.cobar.service.ContService;

/**
 * 
 */
public class AppTest extends TestCase{
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ContService contService = (ContService) context.getBean("contService");
	
	/**
	 * 没有使用对象查询直接使用基本类型则到默认的数据源中去查找数据
	 */
    public void test1(){
    	Cont cont = contService.getContByKey(2L);
    	System.out.println(cont);
    	
    }
    
    /**
     * 测试添加
     */
    public void test2(){
    	Cont cont = new Cont();
    	cont.setName("gd0");
    	//Long taobaoId = new Long(new Random().nextInt(10000));
    	Long taobaoId = Long.valueOf("0");
    	cont.setTaobaoId(taobaoId);
    	contService.addCont(cont);
    	
    	
    	Cont cont2 = new Cont();
    	cont2.setName("gd1");
    	//Long taobaoId = new Long(new Random().nextInt(10000));
    	Long taobaoId2 = Long.valueOf("1");
    	cont2.setTaobaoId(taobaoId2);
    	contService.addCont(cont2);
    	
    	
    	Cont cont3 = new Cont();
    	cont3.setName("gd2");
    	//Long taobaoId = new Long(new Random().nextInt(10000));
    	Long taobaoId3 = Long.valueOf("2");
    	cont3.setTaobaoId(taobaoId3);
    	contService.addCont(cont3);
    }
    
    /**
     * 测试使用对象包含taobaoId属性的进行查找
     * 使用这种方式可以根据taobaoId分库查找
     */
    public void test3(){
    	ContQuery contQuery = new ContQuery();
    	contQuery.setTaobaoId(Long.valueOf("1"));
    	List<Cont> list = contService.getContList(contQuery);
    	if(list != null){
    		System.out.println(list.get(0));
    	}
    }
}
