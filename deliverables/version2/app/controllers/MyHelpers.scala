package controllers

/**
 * Created by Marc on 22/11/2015.
 */
class MyHelpers {
  object MyHelpers {
    import views.html.helper.FieldConstructor
    implicit val myFields = FieldConstructor(views.html.input.apply)
  }
}
