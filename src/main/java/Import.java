import io.CacheMemories;
import io.Mover;
import obj.Memory;
import obj.UserInput;
import processing.Shared;
import processing.check.RunnableMemoryChecker;
import processing.load.RunnableMemoryLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Import
{
    private static void mainProcess()
    {
        final long startTime = System.nanoTime();
        List<Memory> currentMemories = new ArrayList<>(0);

        //Then gather up the staged files
        final UserInput importUio = new UserInput();
        importUio.setStartingFolder(new File("Z:" + File.separator + "Imports" + File.separator + "Stage"));
        importUio.setImported(true);
        System.out.println("Getting STAGE memories...");
        final List<Memory> stagedMemories = RunnableMemoryLoader.gatherMemories(importUio);

        //If we have any staged files
        if (!stagedMemories.isEmpty())
        {
            //Get all the current files on repo
            final UserInput userInput = new UserInput();
            userInput.setImported(false);
            userInput.setStartingFolder(new File("Y:" + File.separator + "SharedFolder" + File.separator + "Pictures and Videos"));
            System.out.println("Getting current memories...");
            currentMemories = RunnableMemoryLoader.gatherCurrentRepoMemories(userInput);

            //Gather up all in Pass that have not been added to repo yet
            final UserInput passedUio = new UserInput();
            passedUio.setStartingFolder(new File("Z:" + File.separator + "Imports" + File.separator + "Pass"));
            passedUio.setImported(false);
            System.out.println("Getting previous passed memories...");
            final List<Memory> passedMemories = RunnableMemoryLoader.gatherMemories(passedUio);
            //add in to are shared ones since these previously passed validations
            currentMemories.addAll(passedMemories);

            final ExecutorService pool = Executors.newFixedThreadPool(4);
            for (final Memory stageMemory : stagedMemories)
            {
                final Runnable runnableMemoryChecker = new RunnableMemoryChecker(stageMemory, stagedMemories, currentMemories);
                pool.execute(runnableMemoryChecker);
            }
            try
            {
                pool.shutdown();
                //Wait for threads to all stop
                pool.awaitTermination(12, TimeUnit.HOURS);

            } catch (final InterruptedException ie)
            {
                ie.printStackTrace();
            }

            //Move memories where they should go
            for (final Memory currentMemory : stagedMemories)
            {
                if (currentMemory.isMatched())
                {
                    Mover.moveImportFileMatched(currentMemory);
                }
                else
                {
                    Mover.movePassed(currentMemory);
                }
            }

            CacheMemories.cacheCurrentMemories(currentMemories);
            userInput.printMetaData();
        }

        final long endTime = System.nanoTime();
        final long totalTime = endTime - startTime;
        System.out.println("Total  check  between " + currentMemories.size() + " starting memories and " + Objects.requireNonNull(stagedMemories).size() + " staged memories took: " + Shared.printTotalTimeTaken(totalTime));
        currentMemories.clear();
    }

    public static void main(final String[] args)
    {
        mainProcess();
    }
}