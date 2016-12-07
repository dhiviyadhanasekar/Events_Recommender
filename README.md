## Event recommendation
This project uses the dataset from Kaggle found here:
https://www.kaggle.com/c/event-recommendation-engine-challenge/data
For this project we only need training.csv and events.csv

We only look at cosine similarity of the event description data here although
there are plenty of features from the dataset that we can use for event recommendation.

The focus of our project here was to try out the MapReduce paradigm and HBase.

You will need a hadoop environment with HBase setup to run this project.

## Steps to run:
1. Create and load the user_interests table
  * In the user_interests directory run the createTable script.
  ```
  ./createTable.sh
  ```
  * Run the MapReduce program (Make sure the input file path is correct for training.csv)
  ```
  ./buildandrun.sh
  ```
2. Create and load the events table
  - Phase 1: Calculate TF_IDF
    * Build and run the scripts in events/TF_IDF (Make sure the input file path is correct for events.csv)
    ```
    ./rebuild.sh
    ./rerun.sh -Dmapreduce.job.reduces=2
    ```
  - Phase 2: Aggregate results from previous phase and load into HBase
    * Create events table
    ```
    ./createTable.sh
    ```
    * Run the MapReduce program (The input to this job is the output of Phase 1)
    Note: Depending on your system resources you may need to add the -Dmapreduce.reduce.shuffle.input.buffer.percent=0.2 (0.7 by default) option
    to rerun.sh to avoid OutOfMemory errors during the shuffle phase.
    ```
    ./rebuild.sh
    ./rerun.sh -Dmapreduce.job.reduces=2
    ```

3. Recommendation
 * Build the Scoring class in the recommend folder
 ```
 ./build.sh
 ```

 * Run recommend.sh.
  ```
  ./recommend.sh "homer" "Half Dome day hike"
  ./recommend.sh "homer" "Bollywood night"
  ```
