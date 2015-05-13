# AndroidForms - MVC Forms for Android
Forms, data validation... They can be annoying sometimes.  This library is here
to try to help you out.  AndroidForms aims to be a simple, Generic form library
for android.  At it's core, there aren't even any dependencies! You could easily
move Source, Drain, and Form into any project you'd like.  Because I wanted a
good way to handle forms on Android, I'm keeping it as a dependency so I can
build out helper Sources and helper Drains, like ViewSource!

## Architecture

Form is a final class.  That means you can't override it.  And you know what?
You shouldn't have to.  All a form does is expose 6 methods:

* public save() -- Saves the form from Source into Drain if Source is valid
* public save(bundle) -- Saves the form from Source into Drain if Source is
  valid.  If Bundle isn't null, it writes bundle data to Drain first.
* public forceSave() -- Saves the form from Source into Drain.
* public forceSave(bundle) -- Saves the form from Source into Drain.  If Bundle
  isn't null, it writes bundle data to Drain first.
* public display() -- Writes the Drain information if not null into the Source
  for initialization.
* public display(bundle) -- Same as display, but will apply Bundle to Source
  first.

All forms are created through a builder, and because of this, hide their
constructor.  You can create a form through Form.Builder\<S,D\>.  The S and D
are the Source and Drain subclasses that you create.  Let's take a look at
those:

### Source

Source is an abstract class that takes some type that you want to wrap.  You've
got to tell it how to create whatever you're wrapping in onCreate.  You'll
also, in onCreate, add your Validators, which we'll get to in a minute.  Most of
Source's functionality is hidden from you, and that's on purpose.  Thus, you can
see:

* protected onCreate -- Creates a new instance of your Source object (whatever you're
  wrapping)
* protected onSourceCreated -- Returns a list of validators to validate the source in form save
* public getSource -- returns a source object

### Drain

Drain is an abstract class that takes some type you want to wrap.  You've got to
tell it how to create whatever you're wrapping in onCreate.  You also need to
specify what to do with these objects on form save in onSave, be it writing to a
database or emitting an event.  Your call.  If you pass a null to the
constructor, the drain remains null until save or forceSave is called.

* protectd onCreate -- Where you make an instance of your Drain object
  (whatever you're wrapping)
* protected onSave -- where you save your object, emitting events and whatnot.
* public getDrain -- returns the drain object or null

### Mappers

Form.Mapper is an interface which has two methods, mapForward and mapBackward.
These are used for transfering data from Source to Drain or vice versa.  They
are also used to help you load data from a bundle into a source or drain, for
initialization.  These should never store any state, and thus can usually be
static.

* public mapForward -- Map from source type to drain type
* public mapBackward -- Map from drain type to source type

### Validators

Validator\<F\> takes a type of object to validate.  The objects you can validate
are ones who's references don't change.  You can't validate a string, per say,
but you can validate an EditText, part of which of course is taking it's string
and doing some validation on it... get it?  There are examples in the samples
package.  Validators store a weak reference to your field, so it needs a
non-null reference to observe.  Your Source will call isValid when it runs
validation, and delegate to onValid if the reference is not null.  The reference
is weak, so we aren't holding onto things we shouldn't be.  You have access to:

* protected onValidate(F) -- validate F.

Some simple reusable validators are included for you, and more will be added in
the future.

## The Builder

Your form is pieced together via Form.Builder\<S,D\>.  S and D are type
parameters that refer to the Source and Drain wrapper objects mentioned above.
This Builder creates a Form\<S,D\> for you and returns a new instance of it with
every call to build().  The builder's constructor forces you to give
non-optional arguments, a Source, a Drain, and a Mapper.  You have access to the
following methods:

* withSourceExtrasMapper(Mapper\<Bundle, S\>) -- Add an optional mapper to map
  from a bundle into your source (in display(bundle))
* withDrainExtrasMapper(Mapper\<Bundle, D\>) -- Add an optional mapper to map
  from a bundle into your drain (in save(bundle) and forceSave(bundle)

Note that for these bundle mappers, you do not need to support mapBackwards, as
it will never be called.

## Licensing

AndroidForms is licensed under the [MIT
Licnese](http://opensource.org/licenses/MIT), (C) 2015 by Alex Hart.


## TODO

* Display / Save should return Object wrapped in Source/Drain
* Losing type information in builder methods
