package logic.generator

import logic.generator.constraint.Constraint

class SerieGenerator extends OeuvreGenerator {
  type O = Serie
  type D = SerieDescriptor

  def generate(desc: SerieDescriptor): Serie = new Serie(desc)

  def regenerate(constraints: List[Constraint], oeuvre: Serie): Serie = null


}