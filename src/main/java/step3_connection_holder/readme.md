### step3

- 在**step2**里在同一个事务中使用了相同的Connection对象，
通过传递Connection对象的方式达到共享的目的，但是这种做法是丑陋的，
step3 搞个ConnectionHolder，且做到线程安全。
