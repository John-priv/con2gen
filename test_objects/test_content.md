--------------------------------------------------
{{VARIABLES}}:
--------------------------------------------------
title: "Recipe"
prep_time: "1 minute"
cook_time: "2 minutes"
total_time: "3 minutes"
servings: "5 servings"
calories: "300 calories"


--------------------------------------------------
{{SECTIONS}}:
--------------------------------------------------
{Summary}:
This is a recipe! Note: The "instructions" section currently contains the specs for lists (unordered and ordered)


{Instructions}:
- Instruction number 1 (unordered)
- Second instruction (unordered)
- 3rd instruction (unordered)
This one isn't a bullet point, but is still under "Instructions"
- Now it's back to bullet points

1. Ordered lists will use either numbered points or double dashes
2. Consecutive numbered points are in the same ordered list
3. Can I handle parsing these as numbered points?
50. What about if numbers are missing --> should this care?
8. What if it drops down a number --> should it be a part of the same ordered list? Should it care?
9. Should ordered lists use the number the user adds (in this case 9), or the actual detected/calculated number (in this case 6)?
## Random header
10. Back to the same list --> Should it be able to continue when broken (if the numbers are consecutive)?
Here's a paragraph with only one line break and no special formatting, should this impact it?
11. Again back to the list

Now there's multiple lines and multiple line breaks

These should become individual paragraphs

Should these be a part of point 11? Should quotes or something be needed to group them together?

12. Should it go back to the same list? If it's strict by numbering, then should it group everything that fits into the same ordered list?

1. This would be a new ordered list
2. Still valid
5. Not valid anymore

1. Another new list
2. Element 2
3. Third element in the list
1. New list (restart the ordered list count at 1)

Now we're on to the "strictly numbered list approach"
1. This would also be a new list
2. Element 2 in this list is going to be multiple paragraphs long

Paragraph 2

Paragraph 3

3. This should be a part of the strictly numbered list approach
4. I think this makes the most sense --> It'd be a bit annoying to have to renumber things as a user, but that's a problem they could resolve
5. The only part that would be confusing is the last element in the list

Should this paragraph be a part of the strictly numbered list approach, bullet #5?

There's no syntax that denotes the end of the list. If the first ones can be multi-line, the last should be too.
I think a possible fix would be to require wrapping of multi-line bullet points. Maybe use curly brackets {} for this?


Test list for the strict, bracketed, multi-line approach
1. The first element is only 1 line long
2. {The second one is also only 1 line long, but bracketed}
3. {The third one is bracketed, multi-line long, and valid
This line is also a part of SBML-3
Should it matter where the bracket is for SBML-3?}
4. {For SBML, should multi-line bracketing end on the first line to end in a curly bracket, or at the first curly bracket encountered?
Like should it end here} or should it end here?}
5. {I think it should end when the first (unescaped) curly bracket is encountered
So in this case it would end here}, and all this line would throw an error/warning as this portion is invalid (imbalanced brackets)}
6. I think that the balanced curly bracket approach is the best
7. {Having the instructions section be very long is also a good test for execution delays/timing issues
As the lines don't start with a curly bracket, they won't be compared against/checked to be a section name or element type}
8. {Adding and unbalanced but escaped curly bracket should be valid
Having a backslash here means that \{ is still technically balanced and shouldn't cause an issue
The regex for this should look for a valid ending bracket that isn't escaped, so \} would also not end it
All of this would be a part of SBML-8
While it's checking for a valid ending curly bracket, it should also be looking for a new section/element type
If a new section or element type is detected, it should end the ordered list, switch to the next section/element type, and flag/raise an error
One more escaped curly bracket (plus a valid ending one) for good measure: \}}


A final option would be to have some "I don't care, just order it" syntax, such as a double dash

Double dash
-- This is element 1 in a "I don't care, order this list for me" list 
-- This would be element 2 in the same "I don't care, order this list for me" list
-- Element 3 in a "Order this list for me" list
-- {Element 4 in an auto-ordering ordered list
This one is multiple lines long.
It behaves just like the SBML list, but with auto-numbering.

This would be a part of a second paragraph in element 4 (multiple line breaks = new paragraph)}
-- Element 5 in an auto-ordering list. The double dashes and manually numbered items can NOT be mixed together in the same list


{sandwiched_duplicate}:
I like sandwiches


{test_section_1}:
This is a test section.
I'm making this section a bit longer
Going to add a this section's name into this one just for funsies (this should generate a log message/console output)
{test_section_1}:
I'm also going to add a section that doesn't exist to this


{fake_section_name}:
That should do nothing (except maybe log/have console output if I add that in for debugging purposes)
This should be a part of {test_section_1} btw


{test_section_2}:
Not sure what to put in this section
More lines to make this uneven


{test_section_3}:
To be or not to be
That is the question
Am I a man
Or am I a muppet
If I'm a man
Then I'm a very manly muppet
Rip Ophelia btw


{duplicated_section}:
Note: This section is only "duplicated" in the template generation workflow (it appears twice in the test html file)
This shouldn't be duplicated in the actual output of the page generation workflow

