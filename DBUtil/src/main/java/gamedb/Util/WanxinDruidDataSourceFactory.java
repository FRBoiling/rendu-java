package gamedb.Util;

import com.alibaba.druid.pool.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

@Slf4j
public class WanxinDruidDataSourceFactory extends UnpooledDataSourceFactory {
    public WanxinDruidDataSourceFactory() {
        this.dataSource=new DruidDataSource();
        log.info("wanxin druid datasource init");
    }
}
