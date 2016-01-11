package logic.generator

abstract class TheaterPlayGenerator extends OeuvreGenerator {
  override type O = TheaterPlay
  override type D = TheaterPlayDescriptor

  def numberOfScenePerAct(act: Int) = 5

}
