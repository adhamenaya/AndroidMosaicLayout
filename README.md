# MosaicLayout
Mosaic layout is android layout to display group of views in more that 92 different patters

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
3. Reading the patter is from left to right then top to bottom.

* Example 1: 8 small blocks

```
 ----------- ----------- ----------- -----------
|			|			|			|			|
|	img 1	|	img 2	|	img 3	|	img 4	|
|	small	|	small	|	small	|	small	|
|			|			|			|			|
| --------- | --------- | --------- | --------- |
|			|			|			|			|
|	img 5	|	img 6	|	img 7	|	img 8	|
|	small	|	small	|	small	|	small	|
|			|			|			|			|
 ----------  ----------- ----------- -----------
 ```
 
 As you notice in the previous table, the layout contains on small squares only.
 Then the layout pattern should be written as: 
 
```
BLOCK_PATTERN pattern[] = { 
		BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
		BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL 
};
```

* Example 2: 1 big block and 4 small blocks

```
 ----------- ----------- ----------- -----------
|						|			|			|
|				    	|	img 2	|	img 3	|
|	big 		big		|	small	|	small	|
|						|			|			|
| 		  img1			| --------- | --------- |
|						|			|			|
|	 		    	    |	img 4	|	img 5	|
|	big			big		|	small	|	small	|
|						|			|			|
 ----------  ----------- ----------- -----------
 ```
 
 As you notice in the previous table, image 1 occupies 4 inner cells creating a big block in the layout.
 Then the layout pattern should be written as: 
 
```
BLOCK_PATTERN pattern[] = { 
		BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
		BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL 
};
```

* Example 3: 1 vertical block, 2 small blocks and 2 horizontal blocks

```
 ----------- ----------- ----------- -----------
|			|			|						|
|			|	img 2	|		  img 3			|
|	vert.	|	small	|	horiz.		horiz.	|
|			|			|						|
|	img 1	| --------- | --------- | --------- |
|			|						|			|
|	 		|		  img 4			|	img 5	|
|	vert.	|	horiz.		horiz.	|	small	|
|			|						|			|
 ----------  ----------- ----------- -----------
 ```
 
 As you notice in the previous table, image 1 occupies 2 inner cells vertically, images 3 and 4 occupies 2 inner cells horizontally/
 Then the layout pattern should be written as: 
 
```
BLOCK_PATTERN pattern[] = { 
		BLOCK_PATTERN.VERTICAL, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL,
		BLOCK_PATTERN.VERTICAL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.SMALL 
};
```

##You can design the layout in 92 different patterns!