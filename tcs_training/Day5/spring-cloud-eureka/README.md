server:
  port: 8761
eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
Eureka Client not to register with ‘itself' because our application should be acting as a server.