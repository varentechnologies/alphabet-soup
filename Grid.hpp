#ifndef ALPHABETSOUP_GRID
#define ALPHABETSOUP_GRID

#include <map>
#include <sstream>
#include <vector>

//! The Grid class stores a grid of letters so that they can be searched
//! later on in order to find words.
class Grid
{
public:

    //! The coordinates structure. Defines the start and
    //! stop x,y positions for words.
    struct Coordinates
    {
        //! Returns the coordinates in a nicely formatted string.
        //! @return The formatted string.
        std::string ToString();

        //! The starting X position for the coordinate.
        int64_t StartX;

        //! The starting Y position for the coordinate.
        int64_t StartY;

        //! The ending X position for the coordinate.
        int64_t StopX;

        //! The starting Y position for the coordinate
        int64_t StopY;
    };

    //! Defines the dimensions of the grid.
    struct Dimensions
    {
        //! The X dimension.
        int X;

        //! The Y dimension.
        int Y;
    };

    //! Default Constructor
    Grid();

    //! Copy Constructor
    //! @param[in] Copy The Grid you wish to copy.
    Grid(const Grid& Copy);

    //! Move Constructor
    //! @param[in] Copy The Grid you wish to move.
    Grid(Grid&& Copy);

    //! Copy Operator
    //! @param[in] Copy The Grid you wish to copy.
    //! @return The copied Grid.
    Grid & operator=(const Grid& Copy);

    //! Move Operator
    //! @param[in] Copy The Grid you wish to move.
    //! @return The moved Grid.
    Grid & operator=(Grid&& Copy);

    //! Destructor
    ~Grid();

    //! Creates the Grid. Must be called before "FindWord" will find words.
    //! @param[in] SStream The string stream defining the Grid layout.
    void CreateGrid(std::stringstream& SStream);

    //! Finds the given word in the grid. CreateGrid and SetDim must be called first.
    //! @param[in] Word The word you wish to find.
    //! @param[out] Coordinates The coordinates of the given word.
    //! @return Whether the word was found or not.
    bool FindWord(const std::string& Word, Coordinates& Coordinates) const;

    //! Sets the dimensions of the grid. Must be called before Created Grid is called.
    //! @param[in] The dimensions of the grid.
    void SetDim(const Dimensions& Dim);

private:

    //! The number of directions that words can be layed out in the grid.
    static constexpr int NumDirections = 8;

    //! All of the different directions words can be layed out in the grid.
    static constexpr Dimensions Directions[NumDirections] = { { 1, 0}, {-1, 0},
                                                              { 0, 1}, { 0,-1},
                                                              { 1, 1}, {-1, 1},
                                                              { 1,-1}, {-1,-1} };

    //! Populates WordMap_ with all of the different words in the grid.
    void GenerateWords();

    //! The two dimensional character array defining the grid.
    char ** CharGrid_;

    //! The dimensions of the grid.
    Dimensions GridSize_;

    //! The map of all of the different words in the grid and their coordinates.
    std::multimap<std::string, Coordinates> WordMap_;
};

//! Overloading the "<" operator allowing coordinates to be populated in maps/sets.
//! @param[in] Left The lefthand side of the operator.
//! @param[in] Right The righthand side of the operator.
//! @return Whether the lefthand side is smaller than the rigthand side.
constexpr bool operator < (const Grid::Coordinates& Left, const Grid::Coordinates& Right) {
    return Left.StartX < Right.StartX || (Left.StartX == Right.StartX && Left.StartY < Right.StartY)
        || (Left.StartX == Right.StartX && Left.StartY == Right.StartY && Left.StopX < Right.StopX)
        || (Left.StartX == Right.StartX && Left.StartY == Right.StartY && Left.StopX == Right.StopX && Left.StopY < Right.StopY);
}

#endif