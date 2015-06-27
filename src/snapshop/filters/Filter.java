/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.PixelImage;

/**
 * An interface for filters that modify images.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @version 1.0
 */
public interface Filter
{
  /**
   * Modifies the image according to this filter's algorithm.
   * 
   * @param the_image The image.
   */
  void filter(PixelImage the_image);

  /**
   * Returns a text description of this filter.
   * 
   * @return a text description of this filter.
   */
  String getDescription();
}
