const convertTime = (time) => {

    return new Date(time).toLocaleDateString('en-us',{
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
    });

}

export default convertTime;
