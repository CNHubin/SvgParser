package com.hubin.svgparser;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void all() {
        Observable.just(1,2,3,4,5,6).all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                //所有的事件都大于2吗
                return integer>2;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                //此次返回时间结果
                System.out.println(aBoolean);
            }
        });
    }

    @Test
    public void con() {
        Observable.just(1,2,3,4,5).contains(3).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

    @Test
    public void any() {
        Observable.just(1,2,3,4,5).any(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer==3;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println(aBoolean);
            }
        });
    }

    @Test
    public void isEmpty() {

        Observable.intervalRange(0,50,0,1000, TimeUnit.MILLISECONDS).skipWhile(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) throws Exception {
                return aLong<2;
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(""+aLong);
            }
        });
    }

    @Test
    public void startWith() {
        //合并两个事件 123 会优先处理

        Flowable observable1 = Flowable.intervalRange(0,4,1,1,TimeUnit.MILLISECONDS);
        Flowable observable2 = Flowable.intervalRange(10,4,1,1,TimeUnit.MILLISECONDS);
        Flowable observable3 = Flowable.intervalRange(20,4,1,1,TimeUnit.MILLISECONDS);

        Flowable.merge(observable2,observable3,observable1).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });
    }
}