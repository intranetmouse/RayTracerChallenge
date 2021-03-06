Feature: Materials

Background:
  Given m ← material()
    And position ← point(0, 0, 0)

Scenario: The default material
  Given m ← material()
  Then m.color = color(1, 1, 1)
    And m.ambient = 0.1
    And m.diffuse = 0.9
    And m.specular = 0.9
    And m.shininess = 200.0

Scenario: Reflectivity for the default material
  Given m ← material()
  Then m.reflective = 0.0

Scenario: Transparency and Refractive Index for the default material
  Given m ← material()
  Then m.transparency = 0.0
    And m.refractive_index = 1.0

Scenario: Lighting with the eye between the light and the surface
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 0, -10), color(1, 1, 1))
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.9, 1.9, 1.9)

Scenario: Lighting with the eye between light and surface, eye offset 45°
  Given eyev ← vector(0, √2/2, -√2/2)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 0, -10), color(1, 1, 1))
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.0, 1.0, 1.0)

Scenario: Lighting with eye opposite surface, light offset 45°
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 10, -10), color(1, 1, 1))
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(0.7363961031, 0.7363961031, 0.7363961031)

Scenario: Lighting with eye in the path of the reflection vector
  Given eyev ← vector(0, -√2/2, -√2/2)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 10, -10), color(1, 1, 1))
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(1.6363961031, 1.6363961031, 1.6363961031)

Scenario: Lighting with the light behind the surface
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 0, 10), color(1, 1, 1))
  When result ← lighting(m, light, position, eyev, normalv)
  Then result = color(0.1, 0.1, 0.1)

Scenario: Lighting with the surface in shadow
  Given eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 0, -10), color(1, 1, 1))
    And in_shadow ← true
  When result ← lighting(m, light, position, eyev, normalv, in_shadow)
  Then result = color(0.1, 0.1, 0.1)

Scenario: Lighting with a pattern applied
  Given m.pattern ← stripe_pattern(color(1, 1, 1), color(0, 0, 0))
    And m.ambient ← 1
    And m.diffuse ← 0
    And m.specular ← 0
    And eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
    And light ← point_light(point(0, 0, -10), color(1, 1, 1))
  When c1 ← lighting(m, light, point(0.9, 0, 0), eyev, normalv, false)
    And c2 ← lighting(m, light, point(1.1, 0, 0), eyev, normalv, false)
  Then c1 = color(1, 1, 1)
    And c2 = color(0, 0, 0)

# From the bonus chapter on Rendering Soft Shadows
# http://www.raytracerchallenge.com/bonus/area-light.html
Scenario Outline: lighting() uses light intensity to attenuate color
  Given w ← default_world()
    And w.light ← point_light(point(0, 0, -10), color(1, 1, 1))
    And shape ← the first object in w
    And shape.material.ambient ← 0.1
    And shape.material.diffuse ← 0.9
    And shape.material.specular ← 0
    And shape.material.color ← color(1, 1, 1)
    And pt ← point(0, 0, -1)
    And eyev ← vector(0, 0, -1)
    And normalv ← vector(0, 0, -1)
  When result ← lighting(shape.material, w.light, pt, eyev, normalv, <intensity>)
  Then result = <result>

  Examples:
    | intensity | result                  |
    | 1.0       | color(1, 1, 1)          |
    | 0.5       | color(0.55, 0.55, 0.55) |
    | 0.0       | color(0.1, 0.1, 0.1)    |

Scenario Outline: lighting() samples the area light
  Given corner ← point(-0.5, -0.5, -5)
    And v1 ← vector(1, 0, 0)
    And v2 ← vector(0, 1, 0)
    And light ← area_light(corner, v1, 2, v2, 2, color(1, 1, 1))
    And shape ← sphere()
    And shape.material.ambient ← 0.1
    And shape.material.diffuse ← 0.9
    And shape.material.specular ← 0
    And shape.material.color ← color(1, 1, 1)
    And eye ← point(0, 0, -5)
    And pt ← <point>
    And eyev ← normalize(eye - pt)
    And normalv ← vector(pt.x, pt.y, pt.z)
  When result ← lighting(shape.material, shape, light, pt, eyev, normalv, 1.0)
  Then result = <result>

  Examples:
    | point                      | result                        |
    | point(0, 0, -1)            | color(0.9965048412, 0.9965048412, 0.9965048412) |
    | point(0, 0.7071, -0.7071)  | color(0.6231828237, 0.6231828237, 0.6231828237) |