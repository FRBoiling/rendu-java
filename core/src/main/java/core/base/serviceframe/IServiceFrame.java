package core.base.serviceframe;

public interface IServiceFrame {
    void initPath();
    void initLibData();
    void initXmlData();
    void initLogger();
    void initDB();
    void initRedis();
    void initOpenServerTime();

    void initServers();

    void intiProtocol();
    void updateXml();
}
