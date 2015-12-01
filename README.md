# MosaicLayout
Mosaic layout is android layout to display group of views in more that 90 different patters

## What is MosaicLayout?
It is UI layout library for android. It displays a group of views and view groups (Images, Text, Layout...) in beautiful decoration. 
It offers a lot of patters (92 different pattern) that can you use. 

## How to use the MosaicLayout?
You can choose a specific patterns from the different options you have. OR don't choose any pattern and let the layout choose its patterns randomly.
If you decided to choose a pattern or more, you have to follow the following notes:

1. Each single pattern can hold 8 blocks (2 rows by 4 columns = 8 blocks).
2. There are only 4 types of blocks can be contained in the layout pattern
	* BIG SQUARE (4 inner cells)
	* SMALL SQUARE (1 inner cell)
	* HORIZONTAL RECTANGLE (2 inner cells aligned horizontally)
	* VERTICAL RECTANGLE (2 inner cells aligned vertically)

For example of designing MosaicLayout pattern:

This is the simplest pattern designing you can have.

 ----------- ----------- ----------- -----------
|			|			|			|			|
|	img 1	|	img 2	|	img 3	|	img 4	|
|			|			|			|			|
 ----------	 ----------- ----------- -----------
|			|			|			|			|
|	img 5	|	img 6	|	img 7	|	img 8	|
|			|			|			|			|
 ----------  ----------- ----------- -----------
 
 NOTE: reading the patter is from left to right then top to bottom.
 
 As you notice in the previous table, the layout contains on small squares only.
 Then the layout pattern should be written as: 
 
```
BLOCK_PATTERN pattern1] = { 
		BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
		BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL 
};
```
