package com.akka.ch2.playingwithactors

import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.SupervisorStrategy.Stop
import com.akka.ch2.playingwithactors.MusicController.Play
import com.akka.ch2.playingwithactors.MusicPlayer.{StartMusic, StopMusic}

object MusicController {

  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props = Props[MusicController]
}

class MusicController extends Actor {
  def receive = {
    case Play =>
      println("Music Started.....")
    case Stop =>
      println("Music Stopped....")
  }
}

object MusicPlayer {
  sealed trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StartMusic extends PlayMsg
}

class MusicPlayer extends Actor {
  def receive = {
    case StopMusic =>
      println("I don't want to stop music")
    case StartMusic =>
      val controller = context.actorOf(MusicController.props, "controller")
      controller ! Play
    case _ =>
      println("Unknown Message")
  }
}

object Creation extends App {
  // Create the 'creation' actor system
  val system = ActorSystem("creation")

  // Create the 'Music Player'
  val player = system.actorOf(Props[MusicPlayer], "player")

  player ! StartMusic

  system.terminate()
}