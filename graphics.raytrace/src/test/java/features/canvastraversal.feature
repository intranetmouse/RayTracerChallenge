Feature: Canvas Traversal
  Test traversing of pixels on a canvas.

Scenario: Basic Across Down Traversal - Wide
  Given t ← acrossDownTraversal(2, 1)

  When first ← getPixelCoordinate(t)
  Then first.x = 0
  And first.y = 0

  When second ← getPixelCoordinate(t)
  Then second.x = 1
  And second.y = 0

  When third ← getPixelCoordinate(t)
  Then third is null


Scenario: Basic Across Down Traversal - Tall
  Given t ← acrossDownTraversal(1, 2)

  When first ← getPixelCoordinate(t)
  Then first.x = 0
  And first.y = 0

  When second ← getPixelCoordinate(t)
  Then second.x = 0
  And second.y = 1

  When third ← getPixelCoordinate(t)
  Then third is null

Scenario: Basic Across Down Traversal - Square 2x2
  Given t ← acrossDownTraversal(2, 2)

  When first ← getPixelCoordinate(t)
  Then first.x = 0
  And first.y = 0

  When second ← getPixelCoordinate(t)
  Then second.x = 1
  And second.y = 0

  When third ← getPixelCoordinate(t)
  Then third.x = 0
  And third.y = 1

  When fourth ← getPixelCoordinate(t)
  Then fourth.x = 1
  And fourth.y = 1

  When fifth ← getPixelCoordinate(t)
  Then fifth is null
