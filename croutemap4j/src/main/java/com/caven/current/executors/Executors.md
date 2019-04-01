# Executors
包含了三个executor接口：
Executor:运行新任务的简单接口
ExecutorService：扩展了Executor，添加了用来管理执行器生命周期和任务生命周期的方法
ScheduleExcutorService：扩展了ExecutorService，支持Future和定期执行任务

## 线程池核心类-ThreadPoolExecutor
### 参数说明
ThreadPoolExecutor一共有七个参数，这七个参数配合起来，构成了线程池强大的功能。
1. corePoolSize：核心线程数量
2. maximumPoolSize：线程最大线程数
3. workQueue：阻塞队列，存储等待执行的任务，很重要，会对线程池运行过程产生重大影响
4. keepAliveTime：线程没有任务执行时最多保持多久时间终止（当线程中的线程数量大于corePoolSize的时候，如果这时没有新的任务提交核心线程外的线程不会立即销毁，而是等待，直到超过keepAliveTime）
5. unit：keepAliveTime的时间单位
6. threadFactory：线程工厂，用来创建线程，有一个默认的工场来创建线程，这样新创建出来的线程有相同的优先级，是非守护线程、设置好了名称）
7. rejectHandler：当拒绝处理任务时(阻塞队列满)的策略（AbortPolicy默认策略直接抛出异常、CallerRunsPolicy用调用者所在的线程执行任务、DiscardOldestPolicy
丢弃队列中最靠前的任务并执行当前任务、DiscardPolicy直接丢弃当前任务） 
初始化方法：由七个参数组合成四个初始化方法 

### 线程池生命周期
- running：能接受新提交的任务，也能处理阻塞队列中的任务
- shutdown：不能处理新的任务，但是能继续处理阻塞队列中任务
- stop：不能接收新的任务，也不处理队列中的任务
- tidying：如果所有的任务都已经终止了，这时有效线程数为0
- terminated：最终状态

### 使用Executor创建线程池
1. Executors.newCachedThreadPool
2. Executors.newFixedThreadPool 
3. Executors.newScheduledThreadPool 
4. Executors.newSingleThreadExecutor

