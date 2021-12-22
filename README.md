# 致用全局sequence（发号器）

### 目录
* <a href="#1">简介</a>
* <a href="#2">性能</a>
* <a href="#3">可用性</a>
* <a href="#4">特性</a> 
* <a href="#5">应用场景</a>
* <a href="#6">代码接⼊</a>
* <a href="#7">SQL初始化</a>
* <a href="#8">运行说明</a>


## <a name="1">简介</a>

基于滴滴（Tinyid）改造成了简易的全局sequence发号器。核心原理就是每个业务系统启动时从全局sequence表预占⼀个号段，
然后再内存中实现原⼦分配唯⼀sequence的操作，当号段快使用完时，自动从调用远端RPC发号器服务拉取下⼀个可用的唯⼀号段（滴滴只
提供了HTTP类型的远程获取号段接⼝,现改为性能更好的RPC远程获取号段接口）。

## <a name="2">性能</a>

由于本地获取sequence是在内存中获取，性能非常高，可以达到纳秒级；当本地号段使用完后，远程获取号段也是基于rpc调用获取也
非常快，耗时⼀般在20ms内。
sequence号段初始化sql，可以指定每次拉取号段的步长，理论上步长越长，并发生成sequence的性能就越高，但浪费也就越多，正常
业务步长设置为500~1000即可。

## <a name="3">可用性</a>
依赖db，当db不可用时，因为客户端有缓存，所以还可以使用⼀段时间。后面有需要还可以配置多个db，则只要有1个db存活，则服务可
用。

## <a name="4">特性</a>
* 全局唯⼀的long型id
* 趋势递增的id，即不保证下⼀个id⼀定比上⼀个⼤
* 非连续性
* 支持批量获取id(最⼤250个)
* 支持生成1,3,5,7,9...序列的id

## <a name="5">应用场景</a>

适用场景 : 只关心id是数字、唯⼀，趋势递增的系统，可以容忍id不连续，有浪费的场景
不适用场景 : 必须ID连续，严格递增

## <a name="6">代码接⼊</a>
* 1、maven依赖

```
<dependency>
    <groupId>com.zhiyong.saas</groupId>
    <artifactId>global-sequence-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```  

* 2、application.properties 添加如下配置

```
#dubbo默认配置
dubbo.registry.protocol=zookeeper
dubbo.registry.address=192.168.16.225:2181
dubbo.provider.protocol.version=1.0.0
#当前业务默认使用的sequence名称
zhiyong.default.sequence.name=global_sequence
``` 

## <a name="7">SQL初始化</a>
执行项目根目录下的init.sql

## <a name="8">运行说明</a>

* 下载项目
* 处理以下几个核心配置

	* 初始化数据库脚本执行							
	  init.sql
	* 修改初始化系统配置（mysql、zk连接配置）					
	  global-sequence-server/src/main/resources/application.yml
	
* 启动全局sequence服务端

    * 运行global-sequence-server模块下的com.zhiyong.saas.server.Application类启动项目

* 需要使用全局sequence的依赖方，pom依赖global-sequence-starter
    * 代码注入：
    
    ```
    #注⼊sequenceId
    @Resource
    private SequenceId sequenceId;
    #方法调用
    ①sequenceId.next();//从配置的默认sequence中获取1个sequenceId
    ②sequenceId.next(100);//从配置的默认sequence中批量获取100个sequenceId
    ③sequenceId.next("study");//从指定sequence中获取1个sequenceId
    ④sequenceId.next("study", 100);//从指定sequence中批量获取100个sequenceId
   ```

## <a name="9">平台作者</a>

### 毛军锐 VX：ybyh8899

