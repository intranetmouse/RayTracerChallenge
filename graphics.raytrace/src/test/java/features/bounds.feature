Feature: Bounding Boxes

Scenario: Creating an empty bounding box
  Given box ← bounding_box(empty)
  Then box.min = point(infinity, infinity, infinity)
    And box.max = point(-infinity, -infinity, -infinity)


Scenario: Creating a bounding box with volume
  Given box ← bounding_box(min=point(-1, -2, -3) max=point(3, 2, 1))
  Then box.min = point(-1, -2, -3)
    And box.max = point(3, 2, 1)


Scenario: Adding points to an empty bounding box
  Given box ← bounding_box(empty)
    And p1 ← point(-5, 2, 0)
    And p2 ← point(7, 0, -3)
  When p1 is added to box
    And p2 is added to box
  Then box.min = point(-5, 0, -3)
    And box.max = point(7, 2, 0)

Scenario: Adding one bounding box to another
  Given box1 ← bounding_box(min=point(-5, -2, 0) max=point(7, 4, 4))
    And box2 ← bounding_box(min=point(8, -7, -2) max=point(14, 2, 8))
  When box2 is added to box1
  Then box1.min = point(-5, -7, -2)
    And box1.max = point(14, 4, 8)

Scenario Outline: Checking to see if a box contains a given point
  Given box ← bounding_box(min=point(5, -2, 0) max=point(11, 4, 7))
    And p ← <point>
  Then box_contains_point(box, p) is <result>

  Examples:
    | point           | result |
    | point(5, -2, 0) | true   |
    | point(11, 4, 7) | true   |
    | point(8, 1, 3)  | true   |
    | point(3, 0, 3)  | false  |
    | point(8, -4, 3) | false  |
    | point(8, 1, -1) | false  |
    | point(13, 1, 3) | false  |
    | point(8, 5, 3)  | false  |
    | point(8, 1, 8)  | false  |

Scenario Outline: Checking to see if a box contains a given box
  Given box ← bounding_box(min=point(5, -2, 0) max=point(11, 4, 7))
    And box2 ← bounding_box(min=<min> max=<max>)
  Then box_contains_box(box, box2) is <result>

  Examples:
    | min              | max             | result |
    | point(5, -2, 0)  | point(11, 4, 7) | true   |
    | point(6, -1, 1)  | point(10, 3, 6) | true   |
    | point(4, -3, -1) | point(10, 3, 6) | false  |
    | point(6, -1, 1)  | point(12, 5, 8) | false  |

Scenario: Transforming a bounding box
  Given box ← bounding_box(min=point(-1, -1, -1) max=point(1, 1, 1))
    And matrix ← rotation_x(π / 4) * rotation_y(π / 4)
  When box2 ← transform(box, matrix)
  Then box2.min = point(-1.4142, -1.7071, -1.7071)
    And box2.max = point(1.4142, 1.7071, 1.7071)