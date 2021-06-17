package profSoftTest

import java.util.*

fun main() {
    val user1 = Client("Ivan", arrayOf(State.DONE))
    val user2 = Client("Peter", arrayOf(State.DONE, State.REJECT))
    val user3 = Client("Vasiliy", arrayOf(State.DONE))

    val publisher1 = Publisher()

    publisher1.subscribe(user1)
    publisher1.subscribe(user2)
    publisher1.subscribe(user3)

    publisher1.unsubscribe(user1)

    publisher1.state = State.REJECT
    publisher1.mainLogic()

    Thread.sleep(3000)

    publisher1.state = State.DONE
    publisher1.mainLogic()
}

interface Subscribers {
    fun update(event:State)
}

interface Publishers {
    fun subscribe(s:Subscribers)
    fun unsubscribe(s:Subscribers)
    fun notifySubscribers()
}

enum class State {
    WAITING,
    DONE,
    REJECT
}

class Client(var name:String, var eventList:Array<State>):Subscribers {
    override fun update(event: State) {
        if (eventList.contains(event)) println("Dear $name, your order is $event")
    }
}

class Publisher:Publishers {
    private var subscribers: LinkedList<Subscribers> = LinkedList()
    var state:State = State.WAITING

    fun mainLogic() {
//        mainProcess1()
//        mainProcess2()
        if (state != State.WAITING) notifySubscribers()
    }

    override fun subscribe(s:Subscribers) {
        subscribers.add(s)
    }

    override fun unsubscribe(s:Subscribers) {
        subscribers.remove(s)
    }

    override fun notifySubscribers() {
        subscribers.forEach { it.update(state) }
    }
}




