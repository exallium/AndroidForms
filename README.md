# AndroidForms
MVC Forms for Android

## Architecture
Forms are generic, in that they don't care about where the data is coming from, or where it's going to.  This was completely intentional.  This way you can build forms for UI to Model Entity, or JSON to Whatever.  The choice is yours.  Hence the wrapper objects noted below.

## Form
Form is the base of the library.  It is only created via a builder.  It is recommended that you use a static
creation method for Form construction in your subclasses.  Form takes these Type arguments:

* S - Source, Where data is coming from, such as a piece of UI
* D - Destination, Where data is going to, such as a Model entity

## Sources and their Holders
A source is linked to a source holder.  The source holder has a single abstract method tied to it:

* onCreate - Create a new source (if necessary) and adds necessary validators (more below)

It also contains a public api of the following:

* getSource - Retrieves the source, creating one if necessary
* addValidator - Adds a validator to an internal list.  Should only be used from onCreate.

## Destinations and their Holders
A destination is linked to a destination holder.  The destination holder has a couple of abstract methods tied to it.  Specifically:

* onCreate - Create a new destination (if necessary)
* onSave - Called when the destination should be saved (if it is saveable)

It also exposes the following public method:

* getDestination - returns the destination, creating it if necessary

## Form Abstraction
The form defines an abstract builder for you to fill out with objects to build your form.  Forms are pluggable
value objects in that you can reuse holder classes and Mappers as you want.  Mappers are intended to be stateless.
You must provide at minimum the following:

* SourceHolder\<S\> within from()
* DestinationHolder\<D\> within to()
* Mapper\<S,D\> within withSDMapper, which maps data from S to D
* Mapper\<D,S\> within withDSMapper, which maps data from D to S

The form builder also allows you to add Bundle mappers:

* Mapper\<Bundle, S\> within withBundleSMapper which maps data from Bundle to S
* Mapper\<Bundle, D\> within withBundleDMapper which maps data from Bundle to D

There is a minimal example in the samples package.  Note that you could refactor things and utilize these
classes with any number of forms.

## Builder
For API niceity, Builders are used for building out forms.  It is recommended that you use a create() static
method to utilize your builder in subclasses.

You must subclass AbstractBuilder, and implement the two abstract methods, but these are minimal, as seen in
samples.

## Validators
You can now build custom validators using the Validator abstract class.  This validator will hold a weak reference
to your field and will handle null checking for you. These should be added via the protected method addValidator in
the onCreate implementation of SourceHolder

## Examples
See samples package
