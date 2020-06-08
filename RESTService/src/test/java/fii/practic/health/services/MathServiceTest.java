package fii.practic.health.services;

import fii.practic.health.service.impl.MathServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class MathServiceTest {

    @InjectMocks
    private MathServiceImpl mathService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenAddIsCalled_shouldReturnCorrectSum(){
        int x = 2;
        int y = 3;

        int result = mathService.computeSum(x, y);
        /*
            If someone somehow modifies my method computeSum(x,y) which returns x + y to return x * y
            Then this test will fail and will let him know that he made a mistake
            He modified a method that was created with a scope and used with a scope
            These type of changes may alter other flow/functionality
         */

        Assertions.assertThat(result).isEqualTo(5);
    }

}
