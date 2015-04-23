# AndroidForms
MVC Forms for Android

## Architecture

# Form
Form is the base of the library.  It takes four parameterized arguments.  These are as follows:

* S - Source, Where data is coming from, such as a piece of UI
* D - Destination, Where data is going to, such as a Model entity
* SH - Source Holder, a subclass of SourceHolder<S> which helps manage the source
* DH - Destination Holder, a subclass of DestinationHolder<D> which helps manage the destination

## Sources and their Holders
A source is linked to a source holder.  The source holder has a couple of abstract methods tied to it.  Specifically:

* onCreate - Create a new source (if necessary)
* isValid(source) - Check whether the given source is valid

## Destinations and their Holders
A destination is linked to a destination holder.  The destination holder has a couple of abstract methods tied to it.  Specifically:

* onCreate - Create a new destination (if necessary)
* onSave - Called when the destination should be saved (if it is saveable)

## Form Abstraction
The form defines 4 abstract methods to be overloaded, which are inspired by the ViewHolder pattern used in recycler view.

* createSourceHolder - Returns a new instance of your defined SourceHolder implementation
* createDestinationHolder - Returns a new instance of your defined DestinationHolder implementation
* populateSource(SourceHolder, DestinationHolder) - Map from Destination to Source
* populateDestination(SourceHolder, DestinationHolder) - Map from Source to Destination

The form also defines two protected methods that can be overloaded to facilitate adding data from bundles:

* populateSourceFromBundle(Source, Bundle)
* populateDestinationFromBundle(Destination, Bundle)

There is a minimal example in the samples package.

## TODO

* Add Validator class which can be "added" within a Source Holder.  For example, if you had 3 fields in your UI form, you would add three validators to a list which would get validated in isValid instead of manually writing out what you want to have happen.  These validators should be created within onCreate.  isValid will become final and no longer require custom implementation.  Instead, you just create a custom validator which will work like a predicate and probably contain one.
