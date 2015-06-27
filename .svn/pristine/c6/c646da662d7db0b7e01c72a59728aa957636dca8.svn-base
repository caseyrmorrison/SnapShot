/*
 * TCSS 305 - Assignment 3: SnapShop
 */

package snapshop.filters;

import snapshop.image.Pixel;
import snapshop.image.PixelImage;

/**
 * Abstract superclass for all image filters.
 * 
 * @author Marty Stepp
 * @author Daniel M. Zimmerman
 * @version 1.0
 */
public abstract class AbstractFilter implements Filter
{
  /**
   * The "Filter" suffix.
   */
  private static final String FILTER_SUFFIX = "Filter";

  /**
   * The description of this filter (will be used on buttons).
   */
  private String my_description;

  // Constructor

  /**
   * Constructs a filter and uses a modified version of the class's name as its
   * description. Any package hierarchy in the name is removed, and the word
   * "Filter" is removed from the end (if present). For example, the class
   * "snapshot.filters.EdgeDetectFilter" would end up with "EdgeDetect" as its
   * description.
   */
  // @ ensures getDescription().equals(getClass().getName());
  protected AbstractFilter()
  {
    final String name = getClass().getName();
    final int dot = name.lastIndexOf('.');
    if (dot >= 0 && name.endsWith(FILTER_SUFFIX))
    {
      // truncate the word "Filter"
      my_description = name.substring(dot + 1, name.length() - FILTER_SUFFIX.length());
    }
    else
    {
      my_description = name.substring(dot + 1, name.length());
    }
  }

  /**
   * Constructs a filter with the specified description.
   * 
   * @param the_description The description.
   */
  // @ ensures getDescription().equals(the_description);
  public AbstractFilter(final String the_description)
  {
    my_description = the_description;
  }

  // Instance Methods

  /**
   * Returns the text description of this filter.
   * 
   * @return the text description of this filter
   */
  @Override
  public String getDescription()
  {
    return my_description;
  }

  /**
   * Applies a "weighting" to each pixel, where its new value is produced by
   * doing a weighted average of the 3x3 grid of pixels around it. For example,
   * A Gaussian blur/softening effect can be achieved by applying the following
   * weights to each pixel:
   * 
   * <pre>
   *    1  2  1
   *    2  4  2
   *    1  2  1
   * </pre>
   * 
   * Since the weights increase the pixel's color value, likely beyond the legal
   * maximum color value of 255, a scale-down is applied based on the sum of the
   * weights.
   * 
   * @param the_image The image.
   * @param the_weights The weights matrix. This must be a non-null 3x3 matrix
   *          or an IllegalArgumentException is thrown.
   * @exception IllegalArgumentException if the weights are invalid.
   */
  protected void weight(final PixelImage the_image, final int[][] the_weights) 
    throws IllegalArgumentException
  {
    checkWeights(the_weights);

    int sum = 0;

    for (final int[] row : the_weights)
    {
      for (final int col : row)
      {
        sum = sum + col;
      }
    }

    if (sum == 0)
    {
      sum = sum + 1;
    }

    weight(the_image, the_weights, sum);
  }

  /**
   * Applies a "weighting" to each pixel, with the given scale-down factor.
   * 
   * @param the_image The image.
   * @param the_weights The weights matrix. This must be a non-null 3x3 matrix
   *          or an IllegalArgumentException is thrown.
   * @param the_scale The scale-down factor.
   * @exception IllegalArgumentException if the weights are invalid.
   * @see #weight(PixelImage, int[][])
   */
  protected void weight(final PixelImage the_image, final int[][] the_weights,
                        final int the_scale) throws IllegalArgumentException
  {
    checkWeights(the_weights);

    final int w = the_image.getWidth(null);
    final int h = the_image.getHeight(null);
    final Pixel[][] old_pixels = the_image.getPixelData();
    final Pixel[][] new_pixels = new Pixel[h][w];

    for (int y = 0; y < h; y++)
    {
      for (int x = 0; x < w; x++)
      {
        // add up 9 neighboring pixels
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, h - 1); j++)
        {
          for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, w - 1); i++)
          {
            // Pixel p = oldPixels[i][j];
            final Pixel p = old_pixels[j][i];
            final int weight = the_weights[y - j + 1][x - i + 1];
            red = red + p.getRed() * weight;
            green = green + p.getGreen() * weight;
            blue = blue + p.getBlue() * weight;
          }
        }

        // account for negative / too high color values
        red = normalize(red / the_scale);
        green = normalize(green / the_scale);
        blue = normalize(blue / the_scale);

        new_pixels[y][x] = new Pixel(red, green, blue);
      }
    }

    the_image.setPixelData(new_pixels);
  }

  /**
   * Checks to see if the specified weights matrix is valid (that is, is
   * non-null and a Pixel.NUM_CHANNELS-square grid).
   * 
   * @param the_weights The weights matrix.
   * @exception IllegalArgumentException if the weights matrix is invalid.
   */
  private void checkWeights(final int[][] the_weights) throws IllegalArgumentException
  {
    if (the_weights == null || the_weights.length != Pixel.NUM_CHANNELS
        || the_weights[0] == null || the_weights[0].length != Pixel.NUM_CHANNELS
        || the_weights[1] == null || the_weights[1].length != Pixel.NUM_CHANNELS
        || the_weights[2] == null || the_weights[2].length != Pixel.NUM_CHANNELS)
    {
      throw new IllegalArgumentException("must pass correctly-sized grid");
    }
  }

  /**
   * Normalizes the specified color value to the range 0-255.
   * 
   * @param the_color The color value.
   * @return the normalized color value.
   */
  protected int normalize(final int the_color)
  {
    return Math.max(Pixel.MIN_COLOR_VALUE, Math.min(Pixel.MAX_COLOR_VALUE, the_color));
  }

  /**
   * Swaps the specified pixels in the image.
   * 
   * @param the_data The image data.
   * @param row_1 The row of the first pixel to swap.
   * @param col_1 The column of the first pixel to swap.
   * @param row_2 The row of the second pixel to swap.
   * @param col_2 The column of the second pixel to swap.
   */
  protected void swap(final Pixel[][] the_data, final int row_1, final int col_1,
                      final int row_2, final int col_2)
  {
    final Pixel temp = the_data[row_1][col_1];
    the_data[row_1][col_1] = the_data[row_2][col_2];
    the_data[row_2][col_2] = temp;
  }
}
