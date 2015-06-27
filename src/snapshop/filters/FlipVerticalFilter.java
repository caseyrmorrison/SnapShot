/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.Pixel;
import snapshop.image.PixelImage;

/**
 * A filter that flips the image vertically.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @version 1.0
 */
public class FlipVerticalFilter extends AbstractFilter
{
  /**
   * Constructs a new flip vertical filter.
   */
  public FlipVerticalFilter()
  {
    super("Flip Vertical");
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
    for (int row = 0; row < the_image.getHeight() / 2; row++)
    {
      for (int col = 0; col < the_image.getWidth(); col++)
      {
        swap(data, row, col, the_image.getHeight() - row - 1, col);
      }
    }
    the_image.setPixelData(data);
  }
}
