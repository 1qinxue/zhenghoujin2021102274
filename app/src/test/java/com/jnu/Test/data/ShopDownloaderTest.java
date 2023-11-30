package com.jnu.Test.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class ShopDownloaderTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doDownloadData() {
        ShopDownloader shopDownloader = new ShopDownloader();
        String strings = shopDownloader.doDownloadData("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
        Assert.assertTrue(strings.contains(" \"name\": \"暨珠海\","));

    }

    @Test
    public void parsonJson() {
        ShopDownloader shopDownloader = new ShopDownloader();
        String json = shopDownloader.doDownloadData("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
        ArrayList<ShopLocation> shopsLocations = shopDownloader.parsonJson(json);
//        // 验证下载的数据是否正确
        Assert.assertEquals(3, shopsLocations.size());

        ShopLocation shop1 = shopsLocations.get(0);
        Assert.assertEquals("暨珠海", shop1.getName());
        Assert.assertEquals("22.255925", shop1.getLatitude().toString());
        Assert.assertEquals("113.541112", shop1.getLongitude().toString());
        Assert.assertEquals("暨南大学珠海校区", shop1.getMemo());

        ShopLocation shop2 = shopsLocations.get(1);
        Assert.assertEquals("沃尔玛", shop2.getName());
        Assert.assertEquals("22.261365", shop2.getLatitude().toString());
        Assert.assertEquals("113.532989", shop2.getLongitude().toString());
        Assert.assertEquals("沃尔玛(前山店)", shop2.getMemo());

        ShopLocation shop3 = shopsLocations.get(2);
        Assert.assertEquals("明珠商", shop3.getName());
        Assert.assertEquals("22.251953", shop3.getLatitude().toString());
        Assert.assertEquals("113.526421", shop3.getLongitude().toString());
        Assert.assertEquals("珠海二城广场", shop3.getMemo());
    }
}