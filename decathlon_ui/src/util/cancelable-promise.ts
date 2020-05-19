export interface CancelablePromise<T> {
    promise: Promise<T>;
    cancel: () => void;
}

const makeCancelable = <T>(promise: Promise<T>): CancelablePromise<T> => {
    let canceled = false;
    const wrapped = new Promise<T>((resolve, reject) => {
       promise
           .then((value) => {
               if (!canceled) {
                   resolve(value);
               } else {
                   reject({canceled: true});
               }
           })
           .catch((error) => {
               if (!canceled) {
                   reject(error);
               } else {
                   reject({canceled: true});
               }
           });
    });
    return {
        promise: wrapped,
        cancel: () => {
            canceled = true;
        }
    }
};

export default makeCancelable;
