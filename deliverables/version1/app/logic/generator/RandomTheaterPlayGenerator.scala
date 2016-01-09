package logic.generator

import logic.generator.constraint.Constraint

object RandomTheaterPlayGenerator extends OeuvreGenerator{
  override type O = TheaterPlay
  override type D = TheaterPlayDescriptor

  // TODO
  override def generate(desc: D): O = ???

  override def regenerate(constraints: List[Constraint], oeuvre: O): O = oeuvre


}
