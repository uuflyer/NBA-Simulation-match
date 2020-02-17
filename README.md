# NBA-Simulation-match

昨天在HUPU里偶然看见一篇帖子，楼主他用`Python`写了一个大致模拟NBA比赛的程序，通过打印在终端页面上；引起了我极大地兴趣！！

因为我也是才学习`JAVA`没多久，就是大四想要找工作才开始学习，好了废话不多说了，直接开始上纲上线！

----------

我的大致思路如下:
[![mind.png](https://i.postimg.cc/65Ln6XSD/mind.png)](https://postimg.cc/qzqzQ9pL)

- Team类主要存储一个队的信息，比如:是否进攻过的标记，总分，总篮板、通过概率选出即将执行操作的球员等功能；

- Player类主要存储一个球员的各种属性，比如: 命中率，名字，位置，被选中的概率，防守属性等；

- Match类就是整个模拟游戏的主要类了，主要对整个比赛的走向掌控；比如: 比赛时间，该哪一队进攻或防守，判断进攻时间，以及各球员得分统计，判断比赛是否加时

- blueTeam和redTeam就是玩家可以任意修改的2队的球员以及属性；只需更改参数即可，不需要去其他类在做更改，这样设计的可扩展性很高

----------

最后，我保留了球员的身高属性；如果有时间或其他有兴趣的可以考虑增加换位防守身高上对命中率的影响等等！！