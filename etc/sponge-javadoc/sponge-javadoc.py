"""
Sponge-JavaDoc is a custom sphinx extension designed to create a role that links to the official Sponge JavaDocs for
the SpongeDocs reStructuredText files.

As a slight tutorial for others wishing to use it in the SpongeDocs:

Simply use the `:javadoc:` role specifying the javadoc you would like to link to. For example:
:javadoc:`com.some.package.SomeClass`
will link to the javadocs for SomeClass as well as display SomeClass as the hyperlink text.

To link to a method, simply use a hash (#) as well as the target method:
:javadoc:`com.some.package.SomeClass#someMethod()`
which will display SomeClass#someMethod()

Internal classes are also supported:
:javadoc:`com.some.package.SomeClass.SomeInternalClass#someMethod()`
which displays SomeClass.SomeInternalClass#someMethod()

Linking to methods with arguments are also supported:
:javadoc:`com.some.package.SomeClass#someMethod(com.some.package.SomeArgumentObject)`
(Note: do not include a variable, just the argument type)

Linking to methods with generic arguments is also supported:
:javadoc:`com.some.package.SomeClass#someMethod(com.some.package.SomeGenericArg<SomeGenericThing>)`
(Note: do not fully declare the path to the generic within the <>. The javadocs do not require it, and I'm certainly
not requiring it. It is only really there for the text that will be displayed and is not used in the javadoc url.)

Multiple arguments within methods as well as primitive types are supported:
:javadoc:`com.some.package.SomeClass#someMethod(com.some.package.SomeClass, double)`

Linking to fields is also supported:
:javadoc:`org.spongepowered.api.text.serializer.TextSerializers#FORMATTING_CODE`
which will display TextSerializers#FORMATTING_CODE

Sponge-Javadoc currently supports liking to the following javadocs:
* The Sponge JavaDocs (https://jd.spongepowered.org)
* Configurate JavaDocs (http://zml2008.github.io/configurate/apidocs/)

Found an issue with Sponge-JavaDoc? Report it to the SpongeDocs repo (https://github.com/SpongePowered/SpongeDocs).
Make sure to include a description of what went wrong, including the javadoc role used (i.e. the
:javadoc:`some.package.SomeClass` thing), the actual displayed text output, and the actual javadoc link output, if
possible.
If an error is thrown into the console that prevents sphinx from continuing (likely a python error), report it to the
docs repo as well! Even if the input to the javadoc role was incorrect, reporting it will allow us to add a check that
will send a 'fake' output so it does not bring sphinx down entirely. Make sure to include the error log in your report
(the error message in the console will tell you where to find the log).

 ~ Original Author: 12AwsomeMan34 (aaronlingerfelt@yahoo.com)

"""
from docutils import nodes, utils


__version__ = '1.0'
__jd_link__ = 'https://jd.spongepowered.org/'
__configurate_link__ = 'http://zml2008.github.io/configurate/apidocs/'


def simple_page_link(text, inliner):
    """
    Creates a simple page link. To explain this, here is an example:

    Input: ('org.spongepowered.api.text.Text', inliner)
    Output: ('Text', 'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/Text.html')

    The input is a 'simple page link'. That is, it is not an internal class, nor does it contain a method. It links to
    one thing, and the created link will link straight to its page.
    """
    # Partition out the class from the last dot so that we may display it on the docs. Example:
    # Input: 'org.spongepowered.api.text.Text'
    # Output: 'Text'
    javadoc_text = text.rpartition('.')[2]
    if '<' in text:
        text = text.rpartition('<')[0]
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + '.html']
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + '.html']


def internal_page_link(text, inliner, text_before_last_object):
    """
    Same as the function above, with the exception that this one specifically deals with internal classes. Here is an
    example of a proper input and expected output:

    Input: ('org.spongepowered.api.text.BookView.Builder', inliner, text_before_last_object)
    Output: ('BookView.Builder', 'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/BookView.Builder
              .html')
    """
    # Takes the text_before_last_object (i.e. the base class/interface/whatever) and appends its internal class.
    # Example:
    # Input: text_before_last_object = 'BookView', text = org.spongepowered.api.text.BookView.Builder
    # Output: 'BookView.Builder'
    javadoc_text = text_before_last_object + '.' + text.rpartition('.')[2]
    if '<' in text:
        text = text.rpartition('<')
    # Remove the javadoc text from the original text. We are going to replace the dots to dashes (/) later on, however
    # for internal classes, this cannot be. Internal classes need the dot in their url. So remove it from here and
    # re-add the one with the dot in it afterwards.
    text = text.replace(javadoc_text, '')
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + javadoc_text + '.html']
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + javadoc_text + '.html']


def simple_with_method_page_link(text, inliner):
    """
    Same as simple_page_link(text, inliner), except this one is for linking to a specific method rather than just the
    page. Note that this is only for no-argument methods. Examples:

    Input: ('org.spongepowered.api.text.BookView#builder()', inliner)
    Output: ('BookView#builder()',
              'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/BookView.html#builder--')
    """
    # Partitions the text before the parenthesis so that we can get the text of the last object. Example:
    # Input: 'org.spongepowered.api.text.BookView#builder'
    # Output: 'BookView#builder'
    javadoc_text = text.rpartition('.')[2]
    # Gets the text from in front of the hash. Using the above output, the output of this would be 'BookView'
    text_object = javadoc_text.rpartition('#')[0]
    # Gets the text from after the hash. Using the above output, the output of this would be 'builder'
    text_method = javadoc_text.rpartition('#')[2].replace('()', '--')
    # Remove the javadoc text from the original text as we will need to modify the url around to accommodate for the
    # method linking.
    text = text.replace(javadoc_text, '')
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + text_object + '.html#' + text_method]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + text_object + '.html#' + text_method]


def internal_with_method_page_link(text, inliner, text_before_last_object):
    """
    Same as above except for internal classes/interfaces/you-get-the-idea. Examples:

    Input: ('org.spongepowered.api.text.BookView.Builder#build()', inliner, text_before_last_object)
    Output: ('BookView.Builder#build()',
              'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/BookView.Builder.html#build--')
    """
    # Takes the text_before_last_object (i.e. the base class/interface/whatever) and appends its internal class.
    # Example:
    # Input: text_before_last_object = 'BookView', text = 'org.spongepowered.api.text.BookView.Builder#build()'
    # Output = 'BookView.Builder#build()'
    javadoc_text = text_before_last_object + '.' + text.rpartition('.')[2]
    # Gets the object text. Using the above example output, this would be 'BookView.Builder'.
    text_object = javadoc_text.rpartition('#')[0]
    # Gets the method text. Using the above example output, this would be 'build()'
    text_method = javadoc_text.rpartition('#')[2]
    # Remove the javadoc text from the original text as we will need to modify the url around to accommodate for the
    # method linking.
    text = text.replace(javadoc_text, '')
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + text_object + '.html#' +
                                text_method.replace('()', '--')]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + text_object + '.html#' + text_method.replace('()', '--')]


def simple_with_arguments(text, inliner, text_before_parenthesis):
    """
    Same as simple_with_method_page_link(), except this one is for methods with arguments. Example:

    Input: ('org.spongepowered.api.util.blockray.BlockRay#maxDistanceFilter(com.flowpowered.math.vector.Vector3d,
              double)', inliner, text_before_parenthesis)
    Output: ('BlockRay#maxDistanceFilter(Vector3d, double)',
      'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/util/blockray/BlockRay.html#maxDistanceFilter
          -com.flowpowered.math.vector.Vector3d-double-')
    """
    # Get the text object from text_before_parenthesis. Example:
    # Input: 'org.spongepowered.api.util.blockray.BlockRay#maxDistanceFilter'
    # Output: 'BlockRay'
    text_object = text_before_parenthesis.rpartition('#')[0].rpartition('.')[2].replace(' ', '')  # TODO: CENTRALIZE
    # Gets the method text. Using the above example, we'd get 'maxDistanceFilter'.
    text_method = text_before_parenthesis.rpartition('#')[2]
    # Gets the text in the parenthesis by removing the part from before the parenthesis and removing the parenthesis
    # themselves. This simply leaving the text that was inside the parenthesis.
    text_in_parenthesis = text.rpartition('(')[2].rpartition(')')[0]  # TODO: APPLY FIX TO OTHER FUNCTIONS
    # Start our javadoc text by taking the text_object#text_method and adding (.
    javadoc_text = text_object + '#' + text_method + '('
    # If there is a comma, then there are multiple arguments.
    if ',' in text_in_parenthesis:
        # For multiple arguments, we need to encase each in their own -
        url_method_text = '-'
        # Go through the arguments split by the comma
        for x in text_in_parenthesis.split(','):
            partitioned_text = x
            if '<' in x:
                inside_generic = x.rpartition('<')[2].rpartition('>')[0]
                if '.' in inside_generic:
                    if '...' in x:
                        javadoc_text += x[:-3].rpartition('<')[0].rpartition('.')[2] + '<' + inside_generic + '>' + ', '
                    else:
                        javadoc_text += x.rpartition('<')[0].rpartition('.')[2] + '<' + inside_generic + '>' + ', '
                else:
                    if '...' in x:
                        javadoc_text += x[:-3].rpartition('.')[2] + ", "
                    else:
                        javadoc_text += x.rpartition('.')[2] + ", "
                partitioned_text = x.rpartition('<')[0]
            else:
                if '...' in x:
                    javadoc_text += x[:-3].rpartition('.')[2] + "..., "
                else:
                    javadoc_text += x.rpartition('.')[2] + ", "
            url_method_text += partitioned_text + '-'
        # Remove the last two characters to prevent a command and a space at the end of the text from the for loop.
        javadoc_text = javadoc_text[:-2] + ')'
    else:
        if '<' in text_in_parenthesis:
            inside_generic = text_in_parenthesis.rpartition('<')[2].rpartition('>')[0]
            if '.' in inside_generic:
                javadoc_text += text_in_parenthesis.rpartition('<')[0].rpartition('.')[2] + '<' + inside_generic + '>'
            else:
                javadoc_text += text_in_parenthesis.rpartition('.')[2] + ')'
            non_generic_text_in_parenthesis = text_in_parenthesis.rpartition('<')[0]
            url_method_text = '-' + non_generic_text_in_parenthesis + '-'
        else:
            if '...' in text_in_parenthesis:
                javadoc_text += text_in_parenthesis[:-3].rpartition('.')[2] + '...)'
            else:
                javadoc_text += text_in_parenthesis.rpartition('.')[2] + ')'
            url_method_text = '-' + text_in_parenthesis + '-'
    text = text_before_parenthesis.rpartition('#')[0].rpartition('.')[0] + '.'  # TODO: APPLY FIX TO OTHER FUNCTIONS
    url_method_text = url_method_text.replace(' ', '')
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + text_object + '.html#' + text_method +
                                url_method_text]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + text_object + '.html#' + text_method + url_method_text]


def internal_with_arguments(text, inliner, text_before_last_object):
    """
    Same as above, except with internal classes 'n stuff.

    Input: ('org.spongepowered.api.text.BookView.Builder#insertPage(int, org.spongepowered.api.text.Text)', inliner,
              text_before_last_object)
    Output: ('BookView.Builder#insertPage(int, Text)',
      'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/BookView.Builder.html#insertPage-int
          -org.spongepowered.api.text.Text-')
    """
    # Get the text object from text_before_parenthesis. Example:
    # Input: text_before_last_object = 'BookView', text =
    #   'org.spongepowered.api.text.BookView.Builder#insertPage(int, org.spongepowered.api.text.Text)'
    # Output 'BookView.Builder'
    text_object = text_before_last_object + '.' + text.rpartition('#')[0].rpartition('.')[2]
    # Gets the method text. Using the above example, we'd get 'insertPage'.
    text_method = text.rpartition('(')[0].rpartition('#')[2]
    # Gets the text in the parenthesis by removing the part from before the parenthesis and removing the ) parenthesis
    # itself. This simply leaving the text that was inside the parenthesis.
    text_in_parenthesis = text.rpartition('(')[2].replace(')', '')
    # Start our javadoc text by taking the text_object#text_method and adding (.
    javadoc_text = text_object + '#' + text_method + '('
    # If there is a comma, then there are multiple arguments.
    if ',' in text_in_parenthesis:
        # For multiple arguments, we need to encase each in their own -
        url_method_text = '-'
        # Go through the arguments split by the comma
        for x in text_in_parenthesis.split(','):
            partitioned_text = x
            if '<' in x:
                partitioned_text = x.rpartition('<')[0]
            if '...' in x:
                javadoc_text += x[:-3].rpartition('.')[2] + "..., "
            else:
                javadoc_text += x.rpartition('.')[2] + ", "
            url_method_text += partitioned_text + '-'
        # Remove the last two characters to prevent a command and a space at the end of the text from the for loop.
        javadoc_text = javadoc_text[:-2] + ')'
    else:
        # Single argument. Just add the argument in.
        if '...' in text_in_parenthesis:
            javadoc_text += text_in_parenthesis[:-3].rpartition('.')[2] + '...)'
        else:
            javadoc_text += text_in_parenthesis.rpartition('.')[2] + ')'
        if '<' in text_in_parenthesis:
            non_generic_text_in_parenthesis = text_in_parenthesis.rpartition('<')[0]
            url_method_text = '-' + non_generic_text_in_parenthesis + '-'
        else:
            url_method_text = '-' + text_in_parenthesis + '-'
    # Replace the text down to just the packages. Remove the classes, methods, arguments, everything else.
    text = text.replace(text_in_parenthesis, '').replace('()', '').replace(text_object, '').replace(text_method, '')\
        .replace('#', '')
    url_method_text = url_method_text.replace(' ', '')
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + text_object + '.html#' + text_method +
                                url_method_text]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + text_object + '.html#' + text_method + url_method_text]


def simple_field(text, inliner):
    """
    Used for linking to a field on a specific page rather than a method. Example:

    Input: ('org.spongepowered.api.text.serializer.TextSerializers#FORMATTING_CODE', inliner)
    Output: ('TextSerializers#FORMATTING_CODE',
              'https://jd.spongepowered.org/4.1.0/org/spongepowered/api/text/serializer/TextSerializers.html
                  #FORMATTING_CODE')
    """
    # Partition out the class from the last dot so that we may display it on the docs. Example:
    # Input: 'org.spongepowered.api.text.serializer.TextSerializers#FORMATTING_CODE'
    # Output: 'TextSerializers'
    if '<' in text:
        object_text = text.rpartition('<')[0].rpartition('.')[2]
        javadoc_text = object_text + '<' + text.rpartition('<')[2].rpartition('>')[0] + '>' + '#' +\
            text.rpartition('#')[2]
    else:
        javadoc_text = text.rpartition('.')[2]
        object_text = javadoc_text.rpartition('#')[0]
    # Gets the field from after the hash.
    field_text = text.rpartition('#')[2]
    text = text.replace(javadoc_text, '').replace('#', '') + object_text
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + '.html#' + field_text]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + '.html#' + field_text]


def internal_field(text, inliner, text_before_last_object):
    """
    Same as above except for internal classes. Fake example:

    Input: ('some.package.SomeClass.SomeInternalClass#SOME_FIELD', inliner, text_before_last_object)
    Output: ('SomeClass.SomeInternalClass#SOME_FIELD', some_jd_link_see_above_function_for_example_output_here)
    """
    # Takes the text_before_last_object (i.e. the base class/interface/whatever) and appends its internal class.
    # Example:
    # Input: text_before_last_object = 'BookView', text = org.spongepowered.api.text.BookView.Builder
    # Output: 'BookView.Builder'
    if '<' in text:
        # If there are generics, consider the original text_before_last_object not usable. As in the generic,
        # it's possible that it was referencing an internal object as well.
        text_before_last_object = text.rpartition('<')[0].rpartition('.')[0].rpartition('.')[2]
        if text_before_last_object[0].isupper():
            # Add the text before the last object and its internal class
            javadoc_text = text_before_last_object + '.' + text.rpartition('<')[0].rpartition('.')[2] + '<' +\
                           text.rpartition('<')[2]
        else:
            javadoc_text = text_before_last_object + '<' + text.rpartition('<')[2]
    else:
        javadoc_text = text_before_last_object + '.' + text.rpartition('.')[2]
    # Gets the field from after the hash.
    field_text = text.rpartition('#')[2]
    # Removes the field part from the text so we can just have the object text.
    object_text = javadoc_text.rpartition('#')[0]
    # Remove the javadoc text from the original text. We are going to replace the dots to dashes (/) later on, however
    # for internal classes, this cannot be. Internal classes need the dot in their url. So remove it from here and
    # re-add the one with the dot in it afterwards. Also remove the hash and the field.
    text = text.replace(object_text, '').replace(field_text, '').replace('#', '')
    # Remove generics in field link
    if '<' in object_text:
        object_text = object_text.rpartition('<')[0].rpartition('>')[2]
    if 'ninja.leaping.configurate' in text:
        return [javadoc_text], [__configurate_link__ + text.replace('.', '/') + object_text + '.html#' + field_text]
    else:
        return [javadoc_text], [__jd_link__ + inliner.document.settings.env.app.config.release + '/' +
                                text.replace('.', '/') + object_text + '.html#' + field_text]


def javadoc_role(name, rawtext, text, lineno, inliner, options={}, content=[]):
    # Replace any new lines and spaces
    text = text.replace('\n', '').replace(' ', '')
    # Gets the text before any specified parenthesis. If there aren't any, then this just returns the original string
    # unmodified. This is useful in-case a method is specified that contains any arguments, and we don't want to touch
    # those in the declaration after this one: text_before_last_class
    text_before_parenthesis = text
    if '(' in text:
        text_before_parenthesis = text.rpartition('(')[0]
    elif '<' in text:
        text_before_parenthesis = text.rpartition('<')[0]
    # Gets the text that is specified before that last specified class/interface/whatever. For example:
    # If com.some.package.SomeClass is specified, then 'package' is returned.
    # If com.some.package.SomeClass.SomeInternalClass is specified, then 'SomeClass' is returned.
    # This is useful for telling if the specified class is an internal class.
    if '.' not in text:
        # If there is no dot in the text, then the documenter goofed. To prevent a python error later on, make
        # a return statement here.
        return [nodes.reference(rawtext, utils.unescape('[Sponge-JavaDoc] Unable to parse ' + text),
                                refuri='https://www.youtube.com/watch?v=KMU0tzLwhbE', **options)], []
    text_before_last_object = text_before_parenthesis.rpartition('.')[0].rpartition('.')[2]
    # If there is no hash (#), then we know that this is not specifying a method. Rather, it is specifying some object.
    # This could be an internal class or a normal class however, so we need to check the text before the object and see
    # if it starts with a capital letter. If it does, then this is an internal class. Otherwise, the text before is
    # part of a package and we can safely ignore it.
    if '#' not in text and not text_before_last_object[0].isupper():
        display_and_url = simple_page_link(text, inliner)
    # If the text before the last object starts with a capital letter, then this is an internal class.
    elif '#' not in text and text_before_last_object[0].isupper():
        display_and_url = internal_page_link(text, inliner, text_before_last_object)
    # If a hash is in the text, then we know that this is referencing a method. If the text_before_last_object does
    # not start with a capital letter, then this is a base class. If the text contains a lone parenthesis, then
    # we know it is referencing a method with no arguments.
    elif '#' in text and not text_before_last_object[0].isupper() and '()' in text:
        display_and_url = simple_with_method_page_link(text, inliner)
    # If a hash is in the text and text_before_last_object starts with a capital letter, then we know this is an
    # internal object linking to a method. If the text contains a lone parenthesis, then we know it is referencing a
    # method with no arguments.
    elif '#' in text and text_before_last_object[0].isupper() and '()' in text:
        display_and_url = internal_with_method_page_link(text, inliner, text_before_last_object)
    # If there is an open parenthesis ( but not a lone one (), then we know that the specified method accepts an
    # argument.
    elif '#' in text and not text_before_last_object[0].isupper() and '(' in text and '()' not in text:
        display_and_url = simple_with_arguments(text, inliner, text_before_parenthesis)
    # If there is an open parenthesis ( but not a lone one (), then we know that the specified method accepts an
    # argument. If the text_before_last_object starts with a capital letter, then this is an internal class.
    elif '#' in text and text_before_last_object[0].isupper() and '(' in text and '()' not in text:
        display_and_url = internal_with_arguments(text, inliner, text_before_last_object)
    # If there is a hash but no opening parenthesis, then this is likely referring to a field.
    elif '#' in text and '(' not in text and not text_before_last_object[0].isupper():
        display_and_url = simple_field(text, inliner)
    # If text_before_last_object starts with a capital letter then this is a field of an internal class
    elif '#' in text and '(' not in text and text_before_last_object[0].isupper():
        display_and_url = internal_field(text, inliner, text_before_last_object)
    # Incorrect input was sent through. Throw some defaults up I suppose.
    else:
        display_and_url = ['[Sponge-JavaDoc] Unable to parse ' + text], ['https://www.youtube.com/watch?v=KMU0tzLwhbE']
    # Second argument here is the text that will be displayed. Third argument here sets the url.
    node = nodes.reference(rawtext, utils.unescape(display_and_url[0][0]), refuri=display_and_url[1][0], **options)
    return [node], []


def setup(app):
    """Sphinx extension setup. Nothing fancy here. Go find something more interesting. :S"""
    app.info('Initializing Sponge-Javadoc version ' + __version__ + '!')
    app.add_role('javadoc', javadoc_role)
    return {'version': __version__}
