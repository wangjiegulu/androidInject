package com.wangjie.androidinject;

import com.wangjie.androidinject.annotation.annotations.net.AIGet;
import com.wangjie.androidinject.annotation.annotations.net.AIParam;
import com.wangjie.androidinject.annotation.annotations.net.AIPost;
import com.wangjie.androidinject.annotation.core.net.RetMessage;
import com.wangjie.androidinject.annotation.util.Params;
import com.wangjie.androidinject.model.Person;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-2-7
 * Time: 下午1:44
 */
public interface PersonWorker {
    @AIGet("http://192.168.2.198:8080/HelloSpringMVC/person/findPersons?aa=#{a3}&bb=#{b3}&cc=#{c3}")
    public RetMessage<Person> getPersonsForGet(@AIParam("a3")String a2, @AIParam("b3") String b2, @AIParam("c3") String c2);

    @AIPost("http://192.168.2.198:8080/HelloSpringMVC/person/findPersons")
    public RetMessage<Person> getPersonsForPost(@AIParam("aa")String a2, @AIParam("bb") String b2, @AIParam("cc") String c2);


    @AIGet("http://192.168.2.198:8080/HelloSpringMVC/person/findPersons")
    public RetMessage<Person> getPersonsForGet2(Params params);

    @AIPost("http://192.168.2.198:8080/HelloSpringMVC/person/findPersons")
    public RetMessage<Person> getPersonsForPost2(Params params);


}
