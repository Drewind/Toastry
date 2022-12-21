package Constants;

/**
 * Monitors the stage of a transaction. Keeps track of where a transaction is within the system.
 * <h3>Possible Results</h1>
 * <ul>
 *  <li>CREATED     - entity has been created.</li>
 *  <li>PENDING     - sale entity has not been saved and is waiting to be processed.</li>
 *  <li>COMPLETED_SUCCESSFULLY - sale has been finished, saved to DB, and approved.</li>
 *  <li>COMPLETED_WITH_ERRORS  - sale has been finished, though encountered an error.</li>
 *  </ul>
 */
public enum TransactionOutcome {
    CREATED,
    PENDING,
    COMPLETED_SUCCESSFULLY,
    COMPLETED_WITH_ERRORS
}
