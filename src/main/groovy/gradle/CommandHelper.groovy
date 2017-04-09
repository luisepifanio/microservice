package gradle

class CommandHelper {

    private static String[] addShellPrefix(String command) {
        String[] commandArray = new String[3]
        commandArray[0] = "sh"
        commandArray[1] = "-c"
        commandArray[2] = command
        return commandArray
    }


    public static List executeOnShell(
            String command,
            File workingDir = null,
            boolean mute = false
    ) {
        return internalExecute(
                addShellPrefix(command),
                workingDir ?: new File(System.properties.'user.dir' as String),
                mute
        )

    }

    public static  List executeCommand(
            String command,
            File workingDir = null,
            boolean mute = false
    ) {
        return internalExecute(
                command.split(' '),
                workingDir ?: new File(System.properties.'user.dir' as String),
                mute
        )
    }

    protected static List internalExecute(
            String[] command,
            File workingDir ,
            boolean mute = false
    ) {
        println """Running on shell:
command: $command
on dir : $workingDir
mute   : $mute
>
"""
        Process process = new ProcessBuilder(command)
                .directory(workingDir)
                .redirectErrorStream(true)
                .start()
        StringBuilder output = new StringBuilder()

        process.inputStream.eachLine {
            output << it

            if(!mute){
                println it
            }

        }
        process.waitFor();
        return [
                process.exitValue(),
                output.toString()
        ]
    }
}
