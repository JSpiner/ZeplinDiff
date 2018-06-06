package net.jspiner.zeplindiff;

import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.api.ApiService;

import org.junit.Assert;
import org.junit.Test;

public class ApiServiceTest {

    @Test
    public void getInstanceTest() {
        Object apiService = Api.getService();
        Assert.assertTrue(
                apiService instanceof ApiService
        );
    }

    @Test
    public void getSingletonInstanceTest() {
        Object firstInstance = Api.getService();
        Object secondInstance = Api.getService();

        Assert.assertEquals(
                firstInstance.getClass(),
                secondInstance.getClass()
        );
        Assert.assertEquals(
                firstInstance.hashCode(),
                secondInstance.hashCode()
        );
    }
}
