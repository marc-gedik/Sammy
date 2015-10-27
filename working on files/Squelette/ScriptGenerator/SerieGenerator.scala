package scriptGenerator

class SerieGenerator extends OeuvreGenerator {
  type O = Serie
  type D = SerieDescriptor
  def generate(desc : SerieDescriptor) : Serie = null
  def regenerate(contraints : Array[Constraint], oeuvre : Serie) : Serie = null
}