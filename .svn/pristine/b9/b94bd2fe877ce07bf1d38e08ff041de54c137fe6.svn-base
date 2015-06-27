/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.Pixel;
import snapshop.image.PixelImage;

/**
 * A filter that flips the image horizontally.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @version 1.0
 */
public class FlipHorizontalFilter extends AbstractFilter
{
  /**
   * Constructs a new flip horizontal filter.
   */
  public FlipHorizontalFilter()
  {
    super("Flip Horizontal");
  }

  /**
   * Filters the specified image.
   * 
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    final Pixel[][] data = the_image.getPixelData();
    for (int row = 0; row < the_image.getHeight(); row++)
    {
      for (int col = 0; col < the_image.getWidth() / 2; col++)
      {
        swap(data, row, col, row, the_image.getWidth() - col - 1);
      }
    }
    the_image.setPixelData(data);
  }
}
