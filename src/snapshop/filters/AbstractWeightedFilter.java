/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.PixelImage;

/**
 * Provides common behavior for all weighted filters.
 * 
 * @author Alan Fowler (acfowler@u.washington.edu)
 * @version Winter 2013
 */
public abstract class AbstractWeightedFilter extends AbstractFilter
{

  /**
   * A 3x3 grid of weights to use for the weighted filter algorithm.
   */
  private final int[][] my_weights;

  /**
   * Constructs a new weighted filter.
   * 
   * @param the_description a descriptive name for this filter
   * @param the_weights the 3x3 grid of weights to use
   */
  public AbstractWeightedFilter(final String the_description, final int[][] the_weights)
  {
    super(the_description);
    my_weights = the_weights.clone();
  }

  /**
   * Filters the specified image.
   * 
   * @param the_image The image.
   */
  @Override
  public void filter(final PixelImage the_image)
  {
    weight(the_image, my_weights);
  }

}
