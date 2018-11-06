redis:
  password: 
  pool:
      maxActive: 300
      maxWait: 5000
      maxIdle: 100
      minIdle: 10
      testOnBorrow: true
  sentinel:
      ips: ip:port,ip:port,ip:port,ip:port #配置
  master:
      name: mymaster  