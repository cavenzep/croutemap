# AQS
## AQS简介
AQS全名：AbstractQueuedSynchronizer，是并发容器J.U.C（java.lang.concurrent）下locks包内的一个类。
它实现了一个FIFO(FirstIn、FisrtOut先进先出)的队列。底层实现的数据结构是一个双向列表。
Sync queue：同步队列，是一个双向列表。包括head节点和tail节点。head节点主要用作后续的调度。
Condition queue：非必须，单向列表。当程序中存在cindition的时候才会存在此列表。
## AQS设计思想
使用Node实现FIFO队列，可以用于构建锁或者其他同步装置的基础框架。
利用int类型标识状态。在AQS类中有一个叫做state的成员变量
基于AQS有一个同步组件，叫做ReentrantLock。在这个组件里，stste表示获取锁的线程数，假如state=0，表示还没有线程获取锁，1表示有线程获取了锁。大于1表示重入锁的数量。
继承：子类通过继承并通过实现它的方法管理其状态（acquire和release方法操纵状态）。
可以同时实现排它锁和共享锁模式（独占、共享），站在一个使用者的角度，AQS的功能主要分为两类：独占和共享。它的所有子类中，要么实现并使用了它的独占功能的api，要么使用了共享锁的功能，而不会同时使用两套api，即便是最有名的子类ReentrantReadWriteLock也是通过两个内部类读锁和写锁分别实现了两套api来实现的。
## AQS的大致实现思路
AQS内部维护了一个CLH队列来管理锁。线程会首先尝试获取锁，如果失败就将当前线程及等待状态等信息包装成一个node节点加入到同步队列sync queue里。
接着会不断的循环尝试获取锁，条件是当前节点为head的直接后继才会尝试。如果失败就会阻塞自己直到自己被唤醒。而当持有锁的线程释放锁的时候，会唤醒队列中的后继线程。

## CountDownLatch


## Semaphore


## CyclicBarrier


## ReentrantLock
java中有两类锁，一类是Synchronized，而另一类就是J.U.C中提供的锁。ReentrantLock与Synchronized都是可重入锁，本质上都是lock与unlock的操作。接下来我们介绍三种J.U.C中的锁，其中 ReentrantLock使用synchronized与之比对介绍。

### ReentrantLock与synchronized的区别

- 可重入性：两者的锁都是可重入的，差别不大，有线程进入锁，计数器自增1，等下降为0时才可以释放锁
- 锁的实现：synchronized是基于JVM实现的（用户很难见到，无法了解其实现），ReentrantLock是JDK实现的。
- 性能区别：在最初的时候，二者的性能差别差很多，当synchronized引入了偏向锁、轻量级锁（自选锁）后，二者的性能差别不大，官方推荐synchronized（写法更容易、在优化时其实是借用了ReentrantLock的CAS
技术，试图在用户态就把问题解决，避免进入内核态造成线程阻塞）
- 功能区别：
（1）便利性：synchronized更便利，它是由编译器保证加锁与释放。ReentrantLock是需要手动释放锁，所以为了避免忘记手工释放锁造成死锁，所以最好在finally中声明释放锁。
（2）锁的细粒度和灵活度，ReentrantLock优于synchronized
### ReentrantLock独有的功能

- 可以指定是公平锁还是非公平锁，sync只能是非公平锁。（所谓公平锁就是先等待的线程先获得锁）
- 提供了一个Condition类，可以分组唤醒需要唤醒的线程。不像是synchronized要么随机唤醒一个线程，要么全部唤醒。
- 提供能够中断等待锁的线程的机制，通过lock.lockInterruptibly()实现，这种机制 ReentrantLock是一种自选锁，通过循环调用CAS操作来实现加锁。性能比较好的原因是避免了进入内核态的阻塞状态。
### 要放弃synchronized？

从上边的介绍，看上去ReentrantLock不仅拥有synchronized的所有功能，而且有一些功能synchronized无法实现的特性。性能方面，ReentrantLock也不比synchronized差，那么到底我们要不要放弃使用synchronized呢？答案是不要这样做。
J.U.C包中的锁定类是用于高级情况和高级用户的工具，除非说你对Lock的高级特性有特别清楚的了解以及有明确的需要，或这有明确的证据表明同步已经成为可伸缩性的瓶颈的时候，否则我们还是继续使用synchronized。相比较这些高级的锁定类，synchronized还是有一些优势的，比如synchronized不可能忘记释放锁。还有当JVM使用synchronized管理锁定请求和释放时，JVM在生成线程转储时能够包括锁定信息，这些信息对调试非常有价值，它们可以标识死锁以及其他异常行为的来源。

## 读写锁：ReentrantReadWriteLock读写锁

在没有任何读写锁的时候才可以取得写入锁(悲观读取，容易写线程饥饿)，也就是说如果一直存在读操作，那么写锁一直在等待没有读的情况出现，这样我的写锁就永远也获取不到，就会造成等待获取写锁的线程饥饿。
平时使用的场景并不多。

## 票据锁：StempedLock

它控制锁有三种模式（写、读、乐观读）。一个StempedLock的状态是由版本和模式两个部分组成。锁获取方法返回一个数字作为票据（stamp），他用相应的锁状态表示并控制相关的访问。数字0表示没有写锁被锁写访问，在读锁上分为悲观锁和乐观锁。