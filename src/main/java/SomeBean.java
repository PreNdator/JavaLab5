import interfaces.SomeInterface;
import interfaces.SomeOtherInterface;

public class SomeBean {
    private SomeInterface field1;
    private SomeOtherInterface field2;

    public void foo(){
        field1.doSomething();
        field2.doSomeOther();
    }
}
