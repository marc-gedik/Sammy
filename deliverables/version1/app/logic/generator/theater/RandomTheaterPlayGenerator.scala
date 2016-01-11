package logic.generator

import logic.generator.constraint.Constraint

object RandomTheaterPlayGenerator extends TheaterPlayGenerator {

  override def generate(desc: D): O = {
    val theaterPlay = new TheaterPlay(desc)
    for (act <- 1 to desc.nbAct) {
      theaterPlay.newAct
      for (scene <- 1 to numberOfScenePerAct(act))
        theaterPlay.add(RandomSceneGenerator.generate(desc))
    }
    theaterPlay
  }

  override def regenerate(constraints: List[Constraint], oeuvre: O): O = oeuvre


}
