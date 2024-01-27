<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Voter extends Model
{
    use HasFactory;

    protected $guarded = [];

    protected $with = ['college'];


    public function contestant(): HasOne
    {
        return $this->hasOne(Contestant::class);
    }

    public function OTPVerification(): HasOne
    {
        return $this->hasOne(OTPVerification::class);
    }


    public function college(): BelongsTo
    {
        return $this->belongsTo(College::class,'college_id', 'id');
    }

    public function contestants(): BelongsToMany {
        return $this->belongsToMany(Contestant::class,'voter_contestants');
    }
}
