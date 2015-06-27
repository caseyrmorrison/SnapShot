/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.PixelImage;

/**
 * A filter that detects edges in the image.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @version 1.0
 */
public class EdgeDetectFilter extends AbstractFilter
{
  /**
   * A 3x3 matrix of weights to use in the filtering algorithm.
   */
  private static final int[][] WEIGHTS = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};

  /**
   * Constructs a new edge detection filter.
   */
  public EdgeDetectFilter()
  {
    super("Edge Detect");
  }

  /**
   * Filters the specified image.
   * 
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    weight(the_image, WEIGHTS);
  }
}
